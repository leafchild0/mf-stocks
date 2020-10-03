package com.kotlinspringvue.backend

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.kotlinspringvue.backend.History
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.io.File


/**
 * Created by romanrybak on 06.09.2020.
 */
@RestController
class HistoryController(private val repository: HistoryRepository) {

    @RequestMapping(value = ["/history"], method = arrayOf(RequestMethod.GET))
    fun getListOfHistory() : List<History>
    {
        return repository.findAll()
    }
}
