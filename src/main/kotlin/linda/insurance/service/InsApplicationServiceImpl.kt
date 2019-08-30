package linda.insurance.service

import linda.insurance.api.BigBrotherService
import linda.insurance.api.PlaidService
import linda.insurance.datasource.dao.CustomerDao
import linda.insurance.model.CustomerCredential
import linda.insurance.model.customer.CustomerInfo
import linda.insurance.model.customer.CustomerStatus
import linda.insurance.model.enums.PolicyTypes
import linda.insurance.util.getPremium
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InsApplicationServiceImpl
    @Autowired constructor(private val plaidService: PlaidService,
                           private val bigBrotherService: BigBrotherService,
                           private val customerDao: CustomerDao): InsApplicationService {

    private val log = LoggerFactory.getLogger(InsApplicationServiceImpl::class.java)

    override suspend fun create(customerCredential: CustomerCredential,
                                customerId: Int) {
        val publicTokenResponse = plaidService.getPublicToken()

        log.info("create() got public_token: $publicTokenResponse")

        val accessTokenResponse = plaidService.getAccessToken(publicTokenResponse.public_token)

        log.info("create() got access_token: $accessTokenResponse")

        // store item
        customerDao.saveCustomer(customerCredential, customerId, accessTokenResponse)
    }

    override suspend fun accountVerified(itemId: String, accountId: String) {

        updateAccountVerified(itemId, accountId)

        // check account balance
        val enoughBalance = checkBalance(itemId, accountId)

        if (enoughBalance) {
            updateAccountAvailable(itemId)
        }

        // get customer information
        val customerItem = customerDao.getCustomerByItemId(itemId)

        // validate
        val customerId = customerItem?.let {
            it.customerId
        } ?: run {
            log.info("customer with itemId $itemId doesn't exist")
            return
        }
        val customerStatus = customerDao.getCustomerById(customerId) ?: run {
            log.info("customer $customerId doesn't exist")
            return
        }

        val lastname = customerStatus.lastName
        val firstname = customerStatus.firstName
        val city = customerStatus.city
        requireNotNull(lastname) { "last name is null" }
        requireNotNull(firstname) { "first name is null" }
        requireNotNull(city) { "city is null" }

        // verify with bigbrother.com
        val isClean = bigBrotherService.verifyClean(lastname, firstname, city)

        if (isClean) {
            // update application status
            log.info("applicant is clean")
        } else {
            log.info("applicant has criminal record")
        }

    }

    override suspend fun getCustomer(customerId: Int): CustomerInfo? {
        val customerStatus = customerDao.getCustomerById(customerId)
        val customerItem = customerDao.getItemByCustomerId(customerId)

        if (customerStatus == null || customerItem == null) {
            return null
        }

        return CustomerInfo(customerId = customerId,
                customerStatus = customerStatus.status,
                itemId = customerItem.itemId,
                accountId = customerItem.accountId,
                itemStatus = customerItem.itemStatus)
    }

    private suspend fun checkBalance(itemId: String, accountId: String?): Boolean {

        // get access token
        val accessToken = customerDao.getAccessToken(itemId)

        val balanceResponse = accessToken?.let{
            plaidService.getBalance(accessToken, accountId)
        }

        val account = balanceResponse?.accounts?.first()
        val available = account?.balances?.available
        if (available != null) {
            return available > getPremium(PolicyTypes.TEN_YEAR.ordinal)
        }
        return false
    }

    private suspend fun updateAccountVerified(itemId: String, accountId: String) {
        customerDao.accountVerified(itemId, accountId)
    }

    private suspend fun updateAccountAvailable(itemId: String) {
        customerDao.accountAvailable(itemId)
    }
}