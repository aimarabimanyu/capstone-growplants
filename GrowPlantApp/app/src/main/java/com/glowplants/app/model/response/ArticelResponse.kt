package com.glowplants.app.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ArticelResponse(
    val error: Boolean,
//    val message: Any,
    val `data`: List<Data>
) {
    @Parcelize
    data class Data(
        val _id: String,
        val imageUrl: String,
        val name: String,
        val category: String,
        val description: String,
        val prescription: List<String>?= mutableListOf(),
        val prevention: List<String>?= mutableListOf(),
        val __v: Int
    ): Parcelable
}