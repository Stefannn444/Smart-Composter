package com.example.smartcomposter.di

import com.example.smartcomposter.data.network.ComposterApi
import com.example.smartcomposter.data.network.ComposterRepository
import com.example.smartcomposter.data.network.NetworkComposterRepository
import com.example.smartcomposter.ui.screens.SmartComposterViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import org.koin.core.scope.get
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val appModule=module{

    single { "http://192.168.182.13" }

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys=true
                    prettyPrint = true
                    isLenient = true
                })
            }
           install(HttpTimeout){
               requestTimeoutMillis = 60000 // e.g., 30 seconds for the whole request
               connectTimeoutMillis = 5000  // e.g., 5 seconds to connect
                socketTimeoutMillis = 30000  // e.g., 30 seconds for data transfer
            }
        }

    }

    single{ ComposterApi(get(),get())}
    single<ComposterRepository>{NetworkComposterRepository(get())}
    viewModel{ SmartComposterViewModel(get()) }
}