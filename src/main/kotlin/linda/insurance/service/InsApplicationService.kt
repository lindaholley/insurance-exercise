package linda.insurance.service

interface InsApplicationService {
    suspend fun create(customerId: Int)

    suspend fun accountVerified(itemId: String, accountId: String)
}