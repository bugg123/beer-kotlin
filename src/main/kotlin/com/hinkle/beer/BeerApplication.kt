package com.hinkle.beer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BeerApplication

fun main(args: Array<String>) {
    runApplication<BeerApplication>(*args)
}
