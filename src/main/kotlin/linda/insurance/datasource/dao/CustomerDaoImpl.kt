package linda.insurance.datasource.dao

import linda.insurance.datasource.repository.CustomerItemsRepository
import linda.insurance.datasource.repository.CustomerStatusRepository
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

    override fun saveCustomer(customerId: Int,
                              accessTokenResponse: PlaidAccessTokenResponse) {
        customerStatusRepo.save(CustomerStatus(customerId = customerId, status = ApplicationStatus.READY.ordinal))
        customerItemsRepo.save(CustomerItem(customerId = customerId,
                itemId = accessTokenResponse.itemId,
                accessToken = accessTokenResponse.accessToken,
                itemStatus = PlaidItemStatus.UNVERIFIED.ordinal))
    }

    override fun accountVerified(itemId: String, accountId: String?) {

        // check existing
        val customerItem = customerItemsRepo.findByItemId(itemId)

        if (customerItem == null) {
            log.warn("updating non-existent item")
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
        customerStatus.status = ApplicationStatus.VERIFIED.ordinal
    }

    override fun getAccessToken(itemId: String): String? {
        val customerItem = customerItemsRepo.findByItemId(itemId)
        return customerItem?.accessToken
    }
}