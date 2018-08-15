package com.hinkle.beer.domain

import javax.persistence.Entity

@Entity
enum class BeerStyles(val type: String){
    STOUT("Stout"), IPA("India Pale Ale"), PORTER("Porter"), NONE("")
}