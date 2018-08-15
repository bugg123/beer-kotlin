package com.hinkle.beer.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Beer{
    private var brewery = ""
    var name = ""
    var style = BeerStyles.NONE
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0;

}

