package com.hinkle.beer.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity

data class Brewery(
  //"id","name","address1","address2","city","state","code","country","phone","website","filepath","descript","last_mod"
    var name: String = "",
    var address1: String = "",
    var city: String = "",
    var state: String = "",
    var country: String = "",
    var phone: String = "",
    var website: String = "",
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0
)