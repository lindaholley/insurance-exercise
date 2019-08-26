package linda.insurance.model.customer

data class CustomerInfo(val customerId: Int,
                        val customerStatus: Int,
                        val itemId: String,
                        val accountId: String?,
                        val itemStatus: Int)