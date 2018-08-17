package com.hinkle.beer.repositories

import com.hinkle.beer.domain.Beer
import com.hinkle.beer.domain.BeerStyles
import com.hinkle.beer.domain.Brewery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class BeerRepositoryTest(@Autowired val entityManager: TestEntityManager,
                         @Autowired val beerRepository: BeerRepository) {
  @Test
  fun `When findByStyle then return beer`() {
    val funkyBuddha = Brewery("Funky Buddha", "Oakland Park, FL", 2010)
    val morningWood = Beer(funkyBuddha, "Morning Wood", BeerStyles.STOUT)

    entityManager.persistAndFlush(funkyBuddha)
    entityManager.persistAndFlush(morningWood)

    assert(beerRepository.findByStyle(BeerStyles.STOUT).contains(morningWood))
  }
}