package linda.insurance.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
class BigBrotherConfig {

    lateinit var host: String
}