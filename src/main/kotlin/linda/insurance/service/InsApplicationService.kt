package linda.insurance.service

import kotlinx.coroutines.CoroutineScope
import linda.insurance.model.CustomerCredential
import linda.insurance.model.customer.CustomerInfo

interface InsApplicationService {
    suspend fun create(customerCredential: CustomerCredential, customerId: Int)

    fun accountVerified(itemId: String, accountId: String, scope: CoroutineScope)

    suspend fun getCustomer(customerId: Int): CustomerInfo?
}