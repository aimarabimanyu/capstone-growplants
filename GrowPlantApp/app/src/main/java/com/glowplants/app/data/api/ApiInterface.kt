package com.glowplants.app.data.api

import com.glowplants.app.model.request.RegisterRequest
import com.glowplants.app.model.response.ArticelResponse
import com.glowplants.app.model.response.PredictResponse
import com.glowplants.app.model.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @POST("users")
    fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @GET("articles")
    suspend fun getArticle(): ArticelResponse

    @POST("predict")
    suspend fun predict(
        @Body request: RequestBody
    ): PredictResponse


}