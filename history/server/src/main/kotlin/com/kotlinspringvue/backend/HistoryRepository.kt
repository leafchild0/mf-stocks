package com.kotlinspringvue.backend

import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository:JpaRepository<History, Long>