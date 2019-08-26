package linda.insurance.service

import linda.insurance.model.customer.CustomerInfo

interface InsApplicationService {
    suspend fun create(customerId: Int)

    suspend fun accountVerified(itemId: String, accountId: String)

    fun getCustomer(customerId: Int): CustomerInfo?
}