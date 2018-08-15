package com.hinkle.beer.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Brewery{
    var name = "";
    var location = "";
    var yearFounded = ""
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0;
}