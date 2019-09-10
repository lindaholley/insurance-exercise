package linda.insurance.model.customer

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "customer_item")
@Entity
data class CustomerItem(

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        @Column(name = "customer_item_id", nullable = false, updatable = false)
        val customerItemId: Int? = null,

        @Column(name = "customer_id", nullable = false)
        val customerId: Int,

        @Column(name = "item_id", nullable = false, updatable = true)
        val itemId: String,

        @Column(name = "access_token", nullable = false, updatable = true)
        val accessToken: String,

        @Column(name = "account_id", nullable = true, updatable = true)
        var accountId: String? = null,

        @Column(name = "item_status", nullable = false, updatable = true)
        var itemStatus: Int
)