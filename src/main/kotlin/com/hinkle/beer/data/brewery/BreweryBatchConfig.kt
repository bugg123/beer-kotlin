package com.hinkle.beer.data.brewery

import com.hinkle.beer.domain.Brewery
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
class BreweryBatchConfig {

  @Autowired
  lateinit var stepBuilderFactory: StepBuilderFactory

  @Autowired
  lateinit var jobBuilderFactory: JobBuilderFactory

  @Value("classPath:/breweries.csv")
  lateinit var breweriesCsv: Resource

  @Bean
  fun readBreweryCsvJob(): Job {
    return jobBuilderFactory
        .get("readBreweryCsvJob")
        .incrementer(RunIdIncrementer())
        .start(step())
        .build()
  }

  fun step(): Step {
    return stepBuilderFactory
        .get("step")
        .chunk<Brewery, Brewery>(5)
        .reader(reader())
        .processor(processor())
        .writer(breweryWriter())
        .build()
  }

  fun processor(): ItemProcessor<Brewery, Brewery> {
    return BreweryDBLogProcessor()
  }

  fun reader(): FlatFileItemReader<Brewery> {
    val itemReader = FlatFileItemReader<Brewery>()
    itemReader.setLineMapper(lineMapper())
    itemReader.setLinesToSkip(1)
    itemReader.setResource(breweriesCsv)
    return itemReader
  }

  fun lineMapper(): LineMapper<Brewery> {
    val lineMapper = DefaultLineMapper<Brewery>()
    val lineTokenizer = DelimitedLineTokenizer()
    lineTokenizer.setNames("id", "name", "address1", "city", "state", "country", "phone", "website")
    lineTokenizer.setIncludedFields(0, 1, 2, 4, 5, 7, 8, 9)
    val fieldSetMapper = BeanWrapperFieldSetMapper<Brewery>()
    fieldSetMapper.setTargetType(Brewery::class.java)
    lineMapper.setLineTokenizer(lineTokenizer)
    lineMapper.setFieldSetMapper(fieldSetMapper)
    return lineMapper
  }

  @Bean
  fun breweryWriter(): JdbcBatchItemWriter<Brewery> {
    val itemWriter = JdbcBatchItemWriter<Brewery>()
    itemWriter.setDataSource(breweryDataSource())
    itemWriter.setSql("INSERT INTO BREWERY (ID, NAME, ADDRESS1, CITY, STATE, COUNTRY, PHONE, WEBSITE) VALUES (:id, :name, :address1, :city, :state, :country, :phone, :website)")
    itemWriter.setItemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<Brewery>())
    return itemWriter
  }

  fun breweryDataSource(): DataSource {
    return EmbeddedDatabaseBuilder().addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
        .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
        .setType(EmbeddedDatabaseType.H2)
        .build()
  }

}