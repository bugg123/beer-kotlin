package com.hinkle.beer.controllers

import com.hinkle.beer.domain.Beer
import com.hinkle.beer.repositories.BeerRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BeerController(private val beerRepository: BeerRepository){

  @GetMapping("/")
  fun getAllBeer(): Iterable<Beer> = beerRepository.findAll()

  @GetMapping("/{id}")
  fun getBeerById(@PathVariable id: Long) = beerRepository.findById(id)

}


