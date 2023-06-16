package com.glowplants.app.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.glowplants.app.data.api.ApiRepository
import com.glowplants.app.model.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody

class BaseViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getArticle() = liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getArticle()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun predict(body: RequestBody) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.predict(body)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}