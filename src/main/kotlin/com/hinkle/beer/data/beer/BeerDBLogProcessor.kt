package com.hinkle.beer.data.beer

import com.hinkle.beer.domain.Beer
import org.springframework.batch.item.ItemProcessor

class BeerDBLogProcessor : ItemProcessor<Beer, Beer> {
  override fun process(beer: Beer): Beer {
    println("Inserting beer: $beer")
    return beer
  }
}