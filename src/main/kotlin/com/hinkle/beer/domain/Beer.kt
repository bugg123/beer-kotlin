package com.hinkle.beer.domain

import org.springframework.context.annotation.Bean
import javax.persistence.*

@Entity
data class Beer(
    var name: String = "",
    @Enumerated
    var style: BeerStyles = BeerStyles.NONE,
    var abv: Double = 0.0,
    var ibu: Double = 0.0,
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0,
//    @OneToOne
    var breweryID: Long? = 0
)

