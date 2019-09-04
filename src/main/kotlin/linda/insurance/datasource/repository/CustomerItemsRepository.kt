package linda.insurance.datasource.repository

import kotlinx.coroutines.reactive.awaitFirstOrNull
import linda.insurance.model.customer.CustomerItem
import org.springframework.stereotype.Repository
import org.springframework.data.r2dbc.function.DatabaseClient

@Repository
class CustomerItemsRepository (private val client: DatabaseClient) {

    suspend fun findByItemId(itemId: String): CustomerItem? =
            client.execute().sql("SELECT * FROM customer_item WHERE item_id = $1")
                    .bind(0, itemId)
                    .`as`(CustomerItem::class.java)
                    .fetch()
                    .one()
                    .awaitFirstOrNull()

    suspend fun findByCustomerId(customerId: Int): CustomerItem? =
            client.execute().sql("SELECT * FROM customer_item WHERE customer_id = $1")
                    .bind(0, customerId)
                    .`as`(CustomerItem::class.java)
                    .fetch()
                    .one()
                    .awaitFirstOrNull()

    suspend fun update(customerItem: CustomerItem) =
            client.execute().sql("UPDATE customer_item SET customer_id = $1, item_id = $2, access_token = $3, account_id = $4, item_status = $5 " +
                    "WHERE customer_item_id = ${customerItem.customerItemId}")
                    .bind(0, customerItem.customerId)
                    .bind(1, customerItem.itemId)
                    .bind(2, customerItem.accessToken)
                    .bind(3, customerItem.accountId)
                    .bind(4, customerItem.itemStatus)
                    .then()
                    .awaitFirstOrNull()


    suspend fun save(customerItem: CustomerItem) =
            client.execute().sql("INSERT INTO customer_item (customer_id, item_id, access_token, account_id, item_status) " +
                    "VALUES(${customerItem.customerId}, ${customerItem.itemId}, ${customerItem.accessToken}, ${customerItem.accountId}, ${customerItem.itemStatus})")
                    .then()
                    .awaitFirstOrNull()
}