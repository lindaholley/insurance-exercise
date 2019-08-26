package linda.insurance.model.plaid

data class PlaidBalanceRequest(val clientId: String,
                               val secret: String,
                               val accessToken: String,
                               val options: AccountIds? = null) {

    data class AccountIds(val accountIds: MutableList<String>)
}