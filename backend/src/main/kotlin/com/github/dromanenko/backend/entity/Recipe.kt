package com.github.dromanenko.backend.entity;

import javax.persistence.*
import java.io.Serializable

@Entity
@Table(
    name = "Recipe", uniqueConstraints = [
        UniqueConstraint(name = "unique_name_ownerId", columnNames = ["name", "ownerId"])
    ]
)
class Recipe : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(name = "name")
    lateinit var name: String

    @Column(name = "description")
    lateinit var description: String

    @ManyToOne
    @JoinColumn(
        name = "ownerId",
        referencedColumnName = "id",
        foreignKey = ForeignKey(
            name = "fk_Recipes_ownerId",
        )
    )
    lateinit var owner: User
}