package com.hinkle.beer.repositories

import com.hinkle.beer.domain.Brewery
import org.springframework.data.repository.CrudRepository

interface BreweryRepository : CrudRepository<Brewery, Long> {
    fun findByLocation(location: String): MutableSet<Brewery>
    fun findByYearFounded(year: Int): MutableSet<Brewery>

}