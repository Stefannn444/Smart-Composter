package com.example.smartcomposter.data.model

data class Plant(
    val name: String,
    val description: String,
    val idealCompost: Composter
)

val plantNeeds = listOf(
    Plant(
        name = "Orchid",
        description = "Prefers dry, airy compost with mild temperature.",
        idealCompost = Composter(
            temperature = 18.0,
            humidity = 40.0,
            airQualityPercentage = 20.0,
            methanePercentage = 5.0
        )
    ),
    Plant(
        name = "Daisy",
        description = "Thrives in well-balanced compost, not too wet.",
        idealCompost = Composter(
            temperature = 22.0,
            humidity = 50.0,
            airQualityPercentage = 30.0,
            methanePercentage = 8.0
        )
    ),
    Plant(
        name = "Tomato",
        description = "Needs warm, moist compost with good aeration.",
        idealCompost = Composter(
            temperature = 28.0,
            humidity = 70.0,
            airQualityPercentage = 40.0,
            methanePercentage = 12.0
        )
    ),
    Plant(
        name = "Fern",
        description = "Loves cool, high-humidity compost environments.",
        idealCompost = Composter(
            temperature = 16.0,
            humidity = 80.0,
            airQualityPercentage = 25.0,
            methanePercentage = 7.0
        )
    )
)

