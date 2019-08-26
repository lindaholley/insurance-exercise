package linda.insurance.controller

import kotlinx.coroutines.runBlocking
import linda.insurance.model.CustomerCredential
import linda.insurance.model.plaid.PlaidWebhookRequest
import linda.insurance.service.InsApplicationService
import linda.insurance.util.WEBHOOK_ERROR_CODE
import linda.insurance.util.WEBHOOK_VERIFIED
import linda.insurance.util.generateCustomerId
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InsApplicationController
@Autowired constructor(@Qualifier("insApplicationServiceImpl") private val insApplicationService: InsApplicationService) {

    private val log = LoggerFactory.getLogger(InsApplicationController::class.java)

    @PostMapping("/create")
    @ResponseBody
    fun createApplication(@RequestBody customerCredential: CustomerCredential) = runBlocking {

        // TODO: validate customer credential
        val customerId = generateCustomerId()

        insApplicationService.create(customerId)
    }

    @PostMapping("/update")
    suspend fun updateStatus(@RequestBody webhookRequest: PlaidWebhookRequest) {

        log.info("updateStatus called with $webhookRequest")

        when (webhookRequest.webhookCode) {
            WEBHOOK_ERROR_CODE -> {
                log.warn("webhook fired error code")
            }
            WEBHOOK_VERIFIED -> {
                insApplicationService.accountVerified(webhookRequest.itemId, webhookRequest.accountId)

                // check account balance
                insApplicationService.checkBalance(webhookRequest.itemId, webhookRequest.accountId)

                // verify with bigbrother.com
            }
            else -> {
                log.warn("webhook fired unknown code")
            }
        }
    }
}
