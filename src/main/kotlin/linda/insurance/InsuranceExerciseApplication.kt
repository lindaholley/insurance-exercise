package linda.insurance

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InsuranceExerciseApplication {

	companion object {

		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(InsuranceExerciseApplication::class.java, *args)
		}
	}
}
