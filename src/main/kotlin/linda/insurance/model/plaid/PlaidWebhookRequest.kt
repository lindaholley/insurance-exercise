package linda.insurance.model.plaid

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlaidWebhookRequest(val webhook_type: String,
                               val webhook_code: String,
                               val item_id: String,
                               val account_id: String,
                               val error: PlaidErrorPayload? = null) {

    data class PlaidErrorPayload(val display_message: String,
                                 val error_code: String,
                                 val error_message: String,
                                 val error_type: String)
}