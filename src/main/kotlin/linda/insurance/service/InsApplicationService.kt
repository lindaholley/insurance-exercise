package linda.insurance.service

import linda.insurance.model.CustomerCredential
import linda.insurance.model.customer.CustomerInfo

interface InsApplicationService {
    suspend fun create(customerCredential: CustomerCredential, customerId: Int)

    suspend fun accountVerified(itemId: String, accountId: String)

    fun getCustomer(customerId: Int): CustomerInfo?
}