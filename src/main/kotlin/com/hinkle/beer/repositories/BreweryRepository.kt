package com.hinkle.beer.repositories

import com.hinkle.beer.domain.Brewery
import org.springframework.data.repository.CrudRepository

interface BreweryRepository : CrudRepository<Brewery, Long> {
  fun findByState(state: String): MutableSet<Brewery>


}