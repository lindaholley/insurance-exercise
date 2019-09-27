package linda.insurance.datasource.repository

import linda.insurance.model.customer.CustomerItem
import org.springframework.data.repository.CrudRepository

interface CustomerItemsRepository: CrudRepository<CustomerItem, Int> {

    fun findByItemId(itemId: String): CustomerItem?

    fun findByCustomerId(customerId: Int): CustomerItem?
}