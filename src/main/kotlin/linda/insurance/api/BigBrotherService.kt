package linda.insurance.api

import linda.insurance.configuration.BigBrotherConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BigBrotherService @Autowired constructor(private val bigBrotherConfig: BigBrotherConfig) {

    // TODO: implement
    fun verifyClean(lastName: String,
                    firstName: String,
                    city: String): Boolean {
        val uri = bigBrotherConfig.host + "/verify"
        return true
    }
}