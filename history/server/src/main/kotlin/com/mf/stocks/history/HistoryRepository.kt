package com.mf.stocks.history

import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository:JpaRepository<History, Long>
