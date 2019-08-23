package linda.insurance.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "plaid")
class PlaidConfig {

    lateinit var institutionId: String
    lateinit var publicKey: String
    lateinit var username: String
    lateinit var password: String
}