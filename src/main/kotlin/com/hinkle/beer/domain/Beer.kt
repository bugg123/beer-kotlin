package com.hinkle.beer.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Beer(
    var brewery: String,
    var name: String,
    var style: BeerStyles,
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0
)

