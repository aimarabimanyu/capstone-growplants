package com.glowplants.app.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.glowplants.app.data.api.ApiInterface
import com.glowplants.app.data.api.ApiRepository

class ViewModelFactory (private val apiInterface: ApiInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(ApiRepository(apiInterface)) as T
        } else if (modelClass.isAssignableFrom(BaseViewModel::class.java)) {
            return BaseViewModel(ApiRepository(apiInterface)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}