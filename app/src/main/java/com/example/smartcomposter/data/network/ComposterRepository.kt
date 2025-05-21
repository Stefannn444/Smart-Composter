package com.example.smartcomposter.data.network

import com.example.smartcomposter.data.model.Composter

interface ComposterRepository{
    suspend fun getStats():Composter
}

class NetworkComposterRepository(
    private val composterApi: ComposterApi
):ComposterRepository{
    override suspend fun getStats(): Composter = composterApi.getStats()
}