package com.github.dromanenko.backend.entity;

import javax.persistence.*
import java.io.Serializable

@Entity
@Table(name = "Users")
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(name = "login", unique = true)
    lateinit var login: String

    @Column(name = "password")
    lateinit var password: String

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    lateinit var addedRecipes: MutableSet<Recipe>
}