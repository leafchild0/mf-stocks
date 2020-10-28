package com.mf.stocks.history

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

/**
 * Created by romanrybak on 07.09.2020.
 */

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class HistoryControllerTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    fun `Test get History in the happy path scenario`() {
        mockMvc.get("/history")
                .andExpect { status { isOk } }
    }

    @Test
    fun `Test add History in the happy path scenario`() {
        val testHistory = History("TestStockName", "", "user", "21", "BUY")
        val expectationTestHistory = History("TestStockName", "", "user", "21", "BUY")
        expectationTestHistory.id = 1
        doTest(testHistory, expectationTestHistory)
    }

    private fun doTest(input: History, expectation: History) {
        mockMvc.post("/history") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(input)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(mapper.writeValueAsString(expectation)) }
        }
    }
}
