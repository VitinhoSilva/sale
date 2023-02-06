package com.jvprojetos17.sale.model

import com.jvprojetos17.sale.enums.Situation
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany


@Entity(name = "purchase")
data class Purchase(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotNull(message = "Informe o usuário!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @NotEmpty(message = "Informe o produto")
    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var products: List<ProductQuantity> = listOf(),

    @Column
    var total: Double,

    @Column
    @Enumerated(EnumType.STRING)
    var situation: Situation = Situation.CONFIRMED,

    @Column
    var createAt: LocalDate = LocalDate.now()

)