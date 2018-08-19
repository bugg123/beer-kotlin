package com.hinkle.beer.data.beer

import com.hinkle.beer.domain.Beer
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.LineMapper
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource


@Configuration
@EnableBatchProcessing
@Profile("beerDB")
class BeerBatchConfig {

  @Autowired
  lateinit var stepBuilderFactory: StepBuilderFactory

  @Autowired
  lateinit var jobBuilderFactory: JobBuilderFactory

  @Value("classPath:/beers.csv")
  lateinit var beerCsv: Resource

  @Bean
  fun readBeerCsvJob(): Job {
    return jobBuilderFactory
        .get("readBeerCsvJob")
        .incrementer(RunIdIncrementer())
        .start(step())
        .build()
  }

  fun step(): Step {
    return stepBuilderFactory
        .get("step")
        .chunk<Beer, Beer>(5)
        .reader(reader())
        .processor(processor())
        .writer(beerWriter())
        .build()
  }

  fun processor(): ItemProcessor<Beer, Beer> {
    return BeerDBLogProcessor()
  }

  fun reader(): FlatFileItemReader<Beer> {
    val itemReader = FlatFileItemReader<Beer>()
    itemReader.setLineMapper(lineMapper())
    itemReader.setLinesToSkip(1)
    itemReader.setResource(beerCsv)
    return itemReader
  }

  fun lineMapper(): LineMapper<Beer> {
    val lineMapper = DefaultLineMapper<Beer>()
    val lineTokenizer = DelimitedLineTokenizer()
    lineTokenizer.setNames("id", "brewery_id", "name", "abv")
    lineTokenizer.setIncludedFields(0, 1, 2, 5)
    val fieldSetMapper = BeanWrapperFieldSetMapper<Beer>()
    fieldSetMapper.setTargetType(Beer::class.java)
    lineMapper.setLineTokenizer(lineTokenizer)
    lineMapper.setFieldSetMapper(fieldSetMapper)
    return lineMapper
  }

  @Bean
  fun beerWriter(): JdbcBatchItemWriter<Beer> {
    val itemWriter = JdbcBatchItemWriter<Beer>()
    itemWriter.setDataSource(beerDataSource())
    itemWriter.setSql("INSERT INTO BEER (ID, BREWERYID, NAME, ABV) VALUES (:id, :breweryID, :name, :abv)")
    itemWriter.setItemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<Beer>())
    return itemWriter
  }

  fun beerDataSource(): DataSource {
    return EmbeddedDatabaseBuilder().addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
        .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
        .setType(EmbeddedDatabaseType.H2)
        .build()
  }

}