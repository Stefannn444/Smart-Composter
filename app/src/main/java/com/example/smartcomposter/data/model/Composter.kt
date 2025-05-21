package com.example.smartcomposter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


//mq1->mq135, air quality
//mq2->mq4, metan
@Serializable
data class Composter(
    @SerialName("avg_temp_C")
    val temperature:Double,
    @SerialName("avg_humidity_percent")
    val humidity:Double,
    @SerialName("mq1_percent")
    val airQualityPercentage:Double,
    @SerialName("mq2_percent")
    val methanePercentage:Double
)
