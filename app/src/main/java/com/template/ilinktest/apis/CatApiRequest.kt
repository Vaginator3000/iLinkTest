package com.template.ilinktest.apis

import retrofit2.http.GET

const val BASE_CAT_URL = "https://aws.random.cat/"

interface CatApiRequest {
    @GET("meow")
    suspend fun getRandomCat(): CatApiData
}