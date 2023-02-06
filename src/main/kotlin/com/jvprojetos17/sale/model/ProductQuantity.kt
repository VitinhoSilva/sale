package com.jvprojetos17.sale.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Entity(name = "product_quantity")
data class ProductQuantity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

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