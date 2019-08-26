package linda.insurance.model.plaid

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlaidPublicTokenResponse(val public_token: String)