package linda.insurance.controller

import kotlinx.coroutines.runBlocking
import linda.insurance.api.PlaidService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InsApplicationController
@Autowired constructor(private val plaidService: PlaidService) {

    private val log = LoggerFactory.getLogger(InsApplicationController::class.java)

    @GetMapping("/create")
    fun createApplication(): ResponseEntity<String> {

        log.info("createApplication()")

        var token: String = "bad token 0"

        runBlocking {
            token = try {
                plaidService.getPublicToken()
            } catch (e: Exception) {
                log.warn(e.message)
                "bad token1"
            }

            log.info("token: $token")
        }
        return ResponseEntity.ok(token)
    }

}
