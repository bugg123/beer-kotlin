package com.hinkle.beer.domain

import java.util.*
import javax.persistence.*

@Entity
data class Beer(
        @OneToOne
        var brewery: Brewery,
        var name: String,
        @Enumerated
        var style: BeerStyles,
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = 0
)

