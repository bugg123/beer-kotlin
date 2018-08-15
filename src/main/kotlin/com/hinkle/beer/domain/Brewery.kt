package com.hinkle.beer.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Brewery(
    var name: String,
    var location: String,
    var yearFounded: String,
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0
)