package com.hinkle.beer.repositories

import com.hinkle.beer.domain.Beer
import com.hinkle.beer.domain.BeerStyles
import org.springframework.data.repository.CrudRepository

interface BeerRepository : CrudRepository<Beer, Long> {

  fun findByStyle(style: BeerStyles): Set<Beer>
}