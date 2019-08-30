package linda.insurance.datasource.dao

import linda.insurance.model.CustomerCredential
import linda.insurance.model.customer.CustomerItem
import linda.insurance.model.customer.CustomerStatus
import linda.insurance.model.plaid.PlaidAccessTokenResponse

interface CustomerDao {

    suspend fun saveCustomer(customerCredential: CustomerCredential,
                     customerId: Int,
                     accessTokenResponse: PlaidAccessTokenResponse)

    suspend fun accountVerified(itemId: String, accountId: String?)

    suspend fun accountAvailable(itemId: String)

    suspend fun getAccessToken(itemId: String): String?

    suspend fun getCustomerById(customerId: Int): CustomerStatus?

    suspend fun getItemByCustomerId(customerId: Int): CustomerItem?

    suspend fun getCustomerByItemId(itemId: String): CustomerItem?
}