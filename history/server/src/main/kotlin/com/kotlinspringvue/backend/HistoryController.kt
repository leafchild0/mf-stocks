package com.kotlinspringvue.backend

import org.springframework.web.bind.annotation.*

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

    @PostMapping("/history")
    fun addHistory(@RequestBody history: History) : History
    {
        return repository.save(history)
    }
}
