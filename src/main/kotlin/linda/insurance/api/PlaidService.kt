package linda.insurance.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import linda.insurance.configuration.PlaidConfig
import linda.insurance.model.PlaidAuth
import linda.insurance.model.PlaidTokenResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PlaidService
@Autowired constructor(private val plaidConfig: PlaidConfig) {

    private val log = LoggerFactory.getLogger(PlaidService::class.java)

    private val client: HttpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    suspend fun getPublicToken(): String {

        log.info("getPublicToken()")

        try {
            val response = client.post<PlaidTokenResponse> {
                url("https://sandbox.plaid.com/sandbox/public_token/create")
                contentType(ContentType.Application.Json)
                body = PlaidAuth(institution_id = plaidConfig.institutionId,
                        initial_products = mutableListOf("auth"),
                        public_key = plaidConfig.publicKey,
                        options = PlaidAuth.PlaidCredential(plaidConfig.username, plaidConfig.password))
            }

            log.info("response from public_token endpoint: $response")

            return response.public_token
        } catch (e: Exception) {
            e.printStackTrace()
            return "bad token2"
        }
    }

}