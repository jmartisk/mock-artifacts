package shop

import io.smallrye.graphql.api.Subscription
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import shop.model.*
import java.util.function.Function

@GraphQLApi
class ShopGraphQLResource {
    @Query
    fun customers(): List<Customer> {
        return Customer.listAll()
    }

    @Query
    fun orders(): List<ShopOrder> {
        return ShopOrder.listAll()
    }


    @Query
    fun products(): List<Product> {
        return Product.listAll() as List<Product>
    }

    @Mutation
    @Transactional
    fun createCustomer(@Valid customer: Customer): Customer {
        customer.persist()
        return customer
    }

    @Mutation
    @Transactional
    fun createOrder(customerId: Long, itemInput: List<OrderItemInput>): ShopOrder {
        val order = ShopOrder()
        val items: List<OrderItem> = itemInput.stream()
                .map(Function<OrderItemInput, OrderItem> { input: OrderItemInput ->
                    val item = OrderItem()
                    item.product = Product.findById(input.productId!!)
                    item.quantity = input.quantity
                    item.persistAndFlush()
                    item
                }).toList()
        order.setItems(items.toMutableList())
        order.persistAndFlush()

        Customer.findById(customerId)!!.addOrder(order)
        newOrdersPublisher.onNext(order)
        return order
    }

    private val newOrdersPublisher: BroadcastProcessor<ShopOrder> = BroadcastProcessor.create()
    private val newOrdersMulti: Multi<ShopOrder> =  Multi.createFrom().publisher(newOrdersPublisher)

    @Subscription
    fun newOrders(): Multi<ShopOrder> {
        return newOrdersMulti
    }
}