package com.hinkle.beer.data.brewery

import com.hinkle.beer.domain.Beer
import com.hinkle.beer.domain.Brewery
import org.springframework.batch.item.ItemProcessor

class BreweryDBLogProcessor : ItemProcessor<Brewery, Brewery> {
  override fun process(brewery: Brewery): Brewery {
    println("Inserting brewery: $brewery")
    return brewery
  }
}