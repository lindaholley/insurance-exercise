package linda.insurance.model.plaid

data class PlaidWebhookRequest(val webhookType: String,
                               val webhookCode: String,
                               val itemId: String,
                               val accountId: String,
                               val error: PlaidErrorPayload) {

    data class PlaidErrorPayload(val displayMessage: String,
                                 val errorCode: String,
                                 val errorMessage: String,
                                 val errorType: String)
}