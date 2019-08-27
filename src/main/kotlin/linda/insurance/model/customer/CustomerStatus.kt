package linda.insurance.model.customer

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "customer_status")
@Entity
data class CustomerStatus(

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        @Column(name = "customer_status_id", nullable = false, updatable = false)
        val customerStatusId: Int? = null,

        @Column(name = "customer_id")
        val customerId: Int,

        @Column(name = "status")
        var status: Int,

        @Column(name = "lastname", nullable = true)
        var lastName: String? = null,

        @Column(name = "firstname", nullable = true)
        var firstName: String? = null,

        @Column(name = "city", nullable = true)
        var city: String? = null)