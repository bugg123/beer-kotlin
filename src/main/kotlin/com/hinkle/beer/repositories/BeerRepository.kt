package com.hinkle.beer.repositories

import com.hinkle.beer.domain.Beer
import org.springframework.data.repository.CrudRepository

interface BeerRepository : CrudRepository<Beer, Long> {

}