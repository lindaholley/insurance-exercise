package linda.insurance.model.plaid

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlaidAccessTokenResponse(val accessToken: String,
                                    val itemId: String)