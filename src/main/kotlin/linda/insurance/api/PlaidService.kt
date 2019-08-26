package linda.insurance.api

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import linda.insurance.configuration.PlaidConfig
import linda.insurance.model.plaid.PlaidAccessTokenRequest
import linda.insurance.model.plaid.PlaidAccessTokenResponse
import linda.insurance.model.plaid.PlaidBalanceRequest
import linda.insurance.model.plaid.PlaidBalanceResponse
import linda.insurance.model.plaid.PlaidPublicTokenRequest
import linda.insurance.model.plaid.PlaidPublicTokenResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PlaidService
@Autowired constructor(private val plaidConfig: PlaidConfig) {

    private val log = LoggerFactory.getLogger(PlaidService::class.java)

    private val client: HttpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer {
                this.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            }
        }
    }

    suspend fun getPublicToken(): PlaidPublicTokenResponse = client.post<PlaidPublicTokenResponse> {
        url("https://sandbox.plaid.com/sandbox/public_token/create")
        contentType(ContentType.Application.Json)
        body = PlaidPublicTokenRequest(institutionId = plaidConfig.institutionId,
                initialProducts = mutableListOf("auth"),
                publicKey = plaidConfig.publicKey,
                options = PlaidPublicTokenRequest.PlaidCredential(plaidConfig.username, plaidConfig.password))
    }

    suspend fun getAccessToken(publicToken: String): PlaidAccessTokenResponse = client.post<PlaidAccessTokenResponse> {
        url("https://sandbox.plaid.com/item/public_token/exchange")
        contentType(ContentType.Application.Json)
        body = PlaidAccessTokenRequest(clientId = plaidConfig.clientId,
                secret = plaidConfig.secret,
                publicToken = publicToken)
    }

    suspend fun getBalance(accessToken: String, accountId: String?): PlaidBalanceResponse = client.post<PlaidBalanceResponse> {
        url("https://sandbox.plaid.com/account/balance/get")
        contentType(ContentType.Application.Json)
        body = PlaidBalanceRequest(clientId = plaidConfig.clientId, secret = plaidConfig.secret, accessToken = accessToken)
    }
}