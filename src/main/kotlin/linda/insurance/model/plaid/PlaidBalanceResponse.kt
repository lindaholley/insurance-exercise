package linda.insurance.model.plaid

data class PlaidBalanceResponse(val current: Double,
                                val available: Double? = null,
                                val limit: Double? = null,
                                val isoCurrencyCode: String? = null,
                                val unofficialCurrencyCode: String? = null)