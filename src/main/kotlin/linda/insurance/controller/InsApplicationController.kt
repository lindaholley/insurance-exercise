package linda.insurance.controller

import kotlinx.coroutines.runBlocking
import linda.insurance.api.PlaidService
import linda.insurance.model.PlaidTokenResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class InsApplicationController
@Autowired constructor(private val plaidService: PlaidService) {

    private val log = LoggerFactory.getLogger(InsApplicationController::class.java)

    @GetMapping("/create")
    fun createApplication(): PlaidTokenResponse = runBlocking {
        plaidService.getPublicToken()
    }

}
