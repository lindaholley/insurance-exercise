package linda.insurance.datasource.repository

import linda.insurance.model.customer.CustomerStatus
import org.springframework.data.repository.CrudRepository

interface CustomerStatusRepository: CrudRepository<CustomerStatus, Int> {

    fun findByCustomerId(customerId: Int): CustomerStatus
}