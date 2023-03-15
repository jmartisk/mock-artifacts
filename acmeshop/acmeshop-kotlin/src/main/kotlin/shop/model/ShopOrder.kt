package shop.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class ShopOrder : PanacheEntity() {

    companion object: PanacheCompanion<ShopOrder> {
    }

    @ManyToOne
    var user: Customer? = null

    @OneToMany
    private var items: MutableList<OrderItem> = ArrayList()
    fun getItems(): List<OrderItem> {
        return items
    }

    fun setItems(items: MutableList<OrderItem>) {
        this.items = items
    }

    fun addItem(quantity: Int?, product: Product?) {
        val item = OrderItem()
        item.quantity = quantity
        item.product = product
        item.persistAndFlush()
        items.add(item)
    }
}