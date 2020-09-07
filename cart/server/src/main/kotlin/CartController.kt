package com.kotlinspringvue.backend

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.kotlinspringvue.backend.Cart
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.io.File


/**
 * Created by romanrybak on 06.09.2020.
 */
@RestController
class CartController {

    @RequestMapping(value = ["/carts"], method = arrayOf(RequestMethod.GET))
    fun getListOfCart() : List<Cart>
    {
        val mapper = jacksonObjectMapper()
        mapper.registerKotlinModule()
        mapper.registerModule(JavaTimeModule())

        val dbCardsString: String = File("./db.json").readText(Charsets.UTF_8)
        return mapper.readValue(dbCardsString)
    }
}
