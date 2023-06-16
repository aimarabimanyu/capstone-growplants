package com.glowplants.app.data.api

import com.glowplants.app.model.request.RegisterRequest
import okhttp3.RequestBody

class ApiRepository (private val apiInterface: ApiInterface) {

    suspend fun register(
        email: String,
        firstname: String,
        grup: String,
        hp: String,
        jenis_kelamin: Int,
        lastname: String,
        password: String,
        referral_code: String,
        role: String,
        strict_password: Boolean,
        tgl_lahir: String
    ) = apiInterface.register(
        RegisterRequest(
            email, firstname, grup, hp, jenis_kelamin, lastname, password, referral_code, role, strict_password, tgl_lahir
        )
    )

    suspend fun getArticle() = apiInterface.getArticle()

    suspend fun predict(body: RequestBody) = apiInterface.predict(body)

}