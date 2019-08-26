package linda.insurance.util

import java.time.Instant

fun generateCustomerId(): Int {
    return Instant.now().epochSecond.toInt()
}

// TODO: hardcoding for now; need to update
fun getPremium(policyType: Int): Double {
    return 100.0
}