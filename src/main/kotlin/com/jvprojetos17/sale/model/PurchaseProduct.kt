package com.jvprojetos17.sale.model

import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity(name = "purchase_product")
data class PurchaseProduct (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    @NotEmpty
    var purchase: Purchase,

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotEmpty
    var product: Product,

    @NotNull(message = "Informe a quantidade!")
    var quantity: Int,

)