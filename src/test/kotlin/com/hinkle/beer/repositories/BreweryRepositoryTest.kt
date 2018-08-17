package com.hinkle.beer.repositories

import com.hinkle.beer.domain.Brewery
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class BreweryRepositoryTest(@Autowired val entityManager: TestEntityManager,
                            @Autowired val breweryRepository: BreweryRepository){

    @Test
    fun `When findByLocation then return brewery`(){
        val threeFloyds = Brewery("3 Floyds", "Chicago, IL", 1996)
        val fakeBrewery = Brewery("fake","fake",2000)

        entityManager.persistAndFlush(threeFloyds)

        val foundThreeFloyds = breweryRepository.findByLocation("Chicago, IL")


        assertTrue(foundThreeFloyds.contains(threeFloyds))
        assertFalse(foundThreeFloyds.contains(fakeBrewery))
    }

    @Test
    fun `When findByYearFounded then return brewery`(){
        val funkyBuddha = Brewery("Funky Buddha","Oakland Park, Fl", 2010)

        entityManager.persistAndFlush(funkyBuddha)


        val foundFunkyBuddha = breweryRepository.findByYearFounded(2010)

        assertTrue(foundFunkyBuddha.contains(funkyBuddha))

    }
}