package shop.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class OrderItem : PanacheEntity() {

    var quantity: Int? = null

    @ManyToOne
    var product: Product? = null
}