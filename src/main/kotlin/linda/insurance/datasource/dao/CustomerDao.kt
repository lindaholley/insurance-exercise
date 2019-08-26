package linda.insurance.datasource.dao

import linda.insurance.model.plaid.PlaidAccessTokenResponse

interface CustomerDao {

    fun saveCustomer(customerId: Int,
                     accessTokenResponse: PlaidAccessTokenResponse)

    fun accountVerified(itemId: String, accountId: String?)

    fun accountAvailable(itemId: String)

    fun getAccessToken(itemId: String): String?
}