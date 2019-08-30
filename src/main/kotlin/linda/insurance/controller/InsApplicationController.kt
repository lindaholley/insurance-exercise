package linda.insurance.controller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import linda.insurance.model.CustomerCredential
import linda.insurance.model.customer.CustomerInfo
import linda.insurance.model.customer.CustomerStatus
import linda.insurance.model.plaid.PlaidWebhookRequest
import linda.insurance.service.InsApplicationService
import linda.insurance.util.WEBHOOK_ERROR_CODE
import linda.insurance.util.WEBHOOK_VERIFIED
import linda.insurance.util.generateCustomerId
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.coroutineContext

@RestController
class InsApplicationController @Autowired constructor(
        @Qualifier("insApplicationServiceImpl") private val insApplicationService: InsApplicationService) {

    private val log = LoggerFactory.getLogger(InsApplicationController::class.java)

    @PostMapping("/create")
    suspend fun createApplication(@RequestBody customerCredential: CustomerCredential) {

        // validate customer credential
        require(customerCredential.firstName.isNotBlank())  { "createApplication(): first name is empty" }
        require(customerCredential.lastName.isNotBlank())  { "createApplication(): last name is empty" }

        val customerId = generateCustomerId()

        log.info("createApplication customerId $customerId")

        insApplicationService.create(customerCredential, customerId)

        log.info("account for $customerId created")
    }

    @PostMapping("/update")
    suspend fun updateStatus(@RequestBody webhookRequest: PlaidWebhookRequest) {

        log.info("updateStatus called with $webhookRequest")

        when (webhookRequest.webhook_code) {
            WEBHOOK_ERROR_CODE -> {
                log.warn("webhook fired error code")
            }
            WEBHOOK_VERIFIED -> {
                insApplicationService.accountVerified(webhookRequest.item_id, webhookRequest.account_id, CoroutineScope(coroutineContext))
            }
            else -> {
                log.warn("webhook fired unknown code")
            }
        }
    }

    /**
     * For debugging purpose
     */
    @GetMapping("/customer/{customerId}")
    suspend fun getCustomer(@PathVariable customerId: Int): CustomerInfo? {

        return insApplicationService.getCustomer(customerId)
    }
}
