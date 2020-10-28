package com.mf.stocks.history

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.io.File
import java.util.function.Consumer

@SpringBootApplication
class HistoryApplication {

    @Value("\${history.db.location}")
    lateinit var dbFilePath: String

    @Bean
    @Profile("dev")
    fun init(repository: HistoryRepository): ApplicationRunner {
        return ApplicationRunner { _: ApplicationArguments? ->
            val mapper = jacksonObjectMapper()
            mapper.registerKotlinModule()
            mapper.registerModule(JavaTimeModule())

            val dbCardsString: String = File(dbFilePath).readText(Charsets.UTF_8)
            var histories: List<History> = mapper.readValue(dbCardsString)

            histories.forEach {
                repository.save(it)
            }

            repository.findAll().forEach(Consumer { x: History? -> println(x) })
        }
    }

    // Fix the CORS errors
    @Bean
    fun simpleCorsFilter(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        // *** URL below needs to match the Vue client URL and port ***
        config.allowedOrigins = listOf("http://localhost:8080")
        config.allowedMethods = listOf("*")
        config.allowedHeaders = listOf("*")
        source.registerCorsConfiguration("/**", config)
        val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(HistoryApplication::class.java, *args)
        }
    }
}
