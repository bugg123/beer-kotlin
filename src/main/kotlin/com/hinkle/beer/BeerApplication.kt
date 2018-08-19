package com.hinkle.beer

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.Scheduled

@SpringBootApplication
class BeerApplication

@Autowired
private lateinit var jobLauncher: JobLauncher

@Autowired
private lateinit var job: Job

fun main(args: Array<String>) {
  runApplication<BeerApplication>(*args)
}


