package linda.insurance.service

interface InsApplicationService {
    suspend fun create(customerId: Int)

    fun accountVerified(itemId: String, accountId: String)

    suspend fun checkBalance(itemId: String, accountId: String?): Boolean
}