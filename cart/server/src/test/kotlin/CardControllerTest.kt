package com.kotlinspringvue.backend

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

/**
 * Created by romanrybak on 07.09.2020.
 */

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class CartControllerTests {

    @Autowired lateinit var mockMvc: MockMvc

    @Test
    fun `Test get Cart in the happy path scenario`() {
        mockMvc.get("/carts")
                .andExpect { status { isOk } }
    }

}
