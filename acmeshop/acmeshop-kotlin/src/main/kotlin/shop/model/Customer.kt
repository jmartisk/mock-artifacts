package shop.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.Size

@Entity
class Customer : PanacheEntity() {

    companion object: PanacheCompanion<Customer> {
    }

    @Size(min = 4)
    lateinit var name: String

    @OneToMany
    private var orders: MutableList<ShopOrder> = ArrayList()
    fun getOrders(): List<ShopOrder> {
        return orders
    }

    fun setOrders(orders: MutableList<ShopOrder>) {
        this.orders = orders
    }

    fun addOrder(order: ShopOrder) {
        orders.add(order)
        order.user = this
    }
}