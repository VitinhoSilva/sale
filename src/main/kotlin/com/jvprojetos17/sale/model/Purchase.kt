package com.jvprojetos17.sale.model

import com.jvprojetos17.sale.enums.Situation
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity(name = "purchase")
data class Purchase(

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    var uuid: UUID? = null,

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
    var createAt: LocalDate = LocalDate.now(),

)
