package com.kotlinspringvue.backend

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class History (val stockName:String, val date:String, val username:String, val amount:String, val type:String)
{
    @Id
    @GeneratedValue
    var id: Long? = null
}
