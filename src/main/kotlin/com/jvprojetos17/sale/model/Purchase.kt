package com.jvprojetos17.sale.model

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

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
    @OneToMany(mappedBy = "purchase")
    var products: List<PurchaseProduct>,

    @Column
    var total: Double,

    @Column
    var createAt: LocalDate = LocalDate.now()

)