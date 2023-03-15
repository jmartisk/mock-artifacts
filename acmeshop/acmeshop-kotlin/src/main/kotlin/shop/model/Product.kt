package shop.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity

@Entity
class Product : PanacheEntity() {

    companion object: PanacheCompanion<Product> {
    }

    lateinit var name: String

}