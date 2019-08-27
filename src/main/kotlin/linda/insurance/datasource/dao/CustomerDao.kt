package linda.insurance.datasource.dao

import linda.insurance.model.CustomerCredential
import linda.insurance.model.customer.CustomerItem
import linda.insurance.model.customer.CustomerStatus
import linda.insurance.model.plaid.PlaidAccessTokenResponse

interface CustomerDao {

    fun saveCustomer(customerCredential: CustomerCredential,
                     customerId: Int,
                     accessTokenResponse: PlaidAccessTokenResponse)

    fun accountVerified(itemId: String, accountId: String?)

    fun accountAvailable(itemId: String)

    fun getAccessToken(itemId: String): String?

    fun getCustomerById(customerId: Int): CustomerStatus?

    fun getItemByCustomerId(customerId: Int): CustomerItem?

    fun getCustomerByItemId(itemId: String): CustomerItem?
}