package linda.insurance.model.plaid

data class PlaidAccessTokenRequest(val clientId: String,
                                   val secret: String,
                                   val publicToken: String)