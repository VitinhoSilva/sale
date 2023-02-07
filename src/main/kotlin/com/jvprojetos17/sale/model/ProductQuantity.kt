package com.jvprojetos17.sale.model

import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity(name = "product_quantity")
data class ProductQuantity(

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    var uuid: UUID? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "purchase_id")
    @NotEmpty
    var purchase: Purchase,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id")
    @NotEmpty
    var product: Product,

    @NotNull(message = "Informe a quantidade!")
    var quantity: Int,

)
