package shop.startup

import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.event.Observes
import jakarta.transaction.Transactional
import shop.model.Customer
import shop.model.Product
import shop.model.ShopOrder

class InitialData {
    @Transactional
    fun initialData(@Observes evt: StartupEvent?) {
        val soap = Product()
        soap.name = "Soap"
        soap.persistAndFlush()
        val alice = Customer()
        alice.name = "Alice"
        alice.persistAndFlush()
        val alicesOrder = ShopOrder()
        alicesOrder.addItem(3, soap)
        alice.addOrder(alicesOrder)
        alicesOrder.persist()
    }
}