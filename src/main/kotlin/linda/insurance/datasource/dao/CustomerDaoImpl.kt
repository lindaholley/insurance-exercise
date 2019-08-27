package linda.insurance.datasource.dao

import linda.insurance.datasource.repository.CustomerItemsRepository
import linda.insurance.datasource.repository.CustomerStatusRepository
import linda.insurance.model.CustomerCredential
import linda.insurance.model.customer.CustomerItem
import linda.insurance.model.customer.CustomerStatus
import linda.insurance.model.enums.ApplicationStatus
import linda.insurance.model.enums.PlaidItemStatus
import linda.insurance.model.plaid.PlaidAccessTokenResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CustomerDaoImpl @Autowired constructor(private val customerStatusRepo: CustomerStatusRepository,
                                             private val customerItemsRepo: CustomerItemsRepository) : CustomerDao {

    private val log = LoggerFactory.getLogger(CustomerDaoImpl::class.java)

    override fun saveCustomer(customerCredential: CustomerCredential,
                              customerId: Int,
                              accessTokenResponse: PlaidAccessTokenResponse) {
        val savedCustomerStatus = customerStatusRepo.save(CustomerStatus(customerId = customerId,
                status = ApplicationStatus.READY.ordinal,
                lastName = customerCredential.lastName,
                firstName = customerCredential.firstName,
                city = customerCredential.city))

        log.info("saved customer status: $savedCustomerStatus")

        val savedCustomerItem = customerItemsRepo.save(CustomerItem(customerId = customerId,
                itemId = accessTokenResponse.itemId,
                accessToken = accessTokenResponse.accessToken,
                itemStatus = PlaidItemStatus.UNVERIFIED.ordinal))

        log.info("saved customer item: $savedCustomerItem")
    }

    override fun accountVerified(itemId: String, accountId: String?) {

        // check existing
        val customerItem = customerItemsRepo.findByItemId(itemId)

        if (customerItem == null) {
            log.warn("updating non-existent item $itemId")
            return
        }

        if (customerItem.itemStatus != PlaidItemStatus.UNVERIFIED.ordinal) {
            log.warn("unexpected update to item $itemId")
            return
        }

        customerItem.itemStatus = PlaidItemStatus.VERIFIED.ordinal
        customerItem.accountId = accountId
        customerItemsRepo.save(customerItem)

        // get existing record of customer status
        val customerStatus = customerStatusRepo.findByCustomerId(customerItem.customerId)

        // update status
        customerStatus?.let {
            it.status = ApplicationStatus.VERIFIED.ordinal

            customerStatusRepo.save(customerStatus)
        }
    }

    override fun accountAvailable(itemId: String) {
        val customerItem = customerItemsRepo.findByItemId(itemId)
        customerItem?.let {
            it.itemStatus = PlaidItemStatus.AVAILABLE.ordinal
            customerItemsRepo.save(customerItem)
        }
    }

    override fun getAccessToken(itemId: String): String? {
        val customerItem = customerItemsRepo.findByItemId(itemId)
        return customerItem?.accessToken
    }

    override fun getCustomerById(customerId: Int): CustomerStatus? {
        return customerStatusRepo.findByCustomerId(customerId)
    }

    override fun getItemByCustomerId(customerId: Int): CustomerItem? {
        return customerItemsRepo.findByCustomerId(customerId)
    }

    override fun getCustomerByItemId(itemId: String): CustomerItem? {
        return customerItemsRepo.findByItemId(itemId)
    }
}