package com.jvprojetos17.sale.model

import com.jvprojetos17.sale.enums.Status
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity(name = "stock")
data class Stock (

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "product_id")
    @NotEmpty
    var product: Product,

    @Column
    @NotNull
    var availableQuantity: Int
)