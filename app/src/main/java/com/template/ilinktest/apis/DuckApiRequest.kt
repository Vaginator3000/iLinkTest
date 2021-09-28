package com.template.ilinktest.apis

import retrofit2.http.GET

const val BASE_DUCK_URL = "https://random-d.uk/api/"

interface DuckApiRequest {
    @GET("random")
    suspend fun getRandomDuck(): DuckApiData
}