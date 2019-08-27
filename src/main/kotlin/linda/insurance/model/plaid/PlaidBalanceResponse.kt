package linda.insurance.model.plaid

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlaidBalanceResponse(val accounts: MutableList<Accounts>) {

    data class Accounts(val account_id: String,
                        val balances: Balances,
                        val mask: String,
                        val name: String,
                        val official_name: String? = null,
                        val subtype: String,
                        val type: String)

    data class Balances(val current: Double,
                        val available: Double? = null,
                        val limit: Double? = null,
                        val iso_currency_code: String? = null,
                        val unofficial_currency_code: String? = null)
}