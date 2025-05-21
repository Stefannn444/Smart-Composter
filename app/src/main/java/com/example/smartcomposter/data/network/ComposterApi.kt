package com.example.smartcomposter.data.network

import com.example.smartcomposter.data.model.Composter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class ComposterApi(
    private val httpClient: HttpClient,
    private val baseUrl:String
    ) {

    suspend fun getStats(): Composter {
        return httpClient.get(baseUrl){
            url{
                appendPathSegments("data")
            }
        }.body()
    }
}