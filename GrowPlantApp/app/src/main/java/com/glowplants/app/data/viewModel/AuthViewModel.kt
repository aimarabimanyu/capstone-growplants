package com.glowplants.app.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.glowplants.app.data.api.ApiRepository
import com.glowplants.app.model.Resource
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val apiRepository: ApiRepository) : ViewModel() {

//    fun login(username: String, password: String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(Resource.success(data = apiRepository.login(username, password)))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//        }
//    }

    fun register(
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
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.register(email, firstname, grup, hp, jenis_kelamin, lastname, password, referral_code, role, strict_password, tgl_lahir)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
