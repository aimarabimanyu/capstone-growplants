package com.glowplants.app.model.response

data class PredictResponse(
    val error: Boolean,
    val message: String,
    val `data`: Data
) {
    data class Data(
        val predicted_disease: String,
        val articles: List<Article>
    ) {
        data class Article(
            val _id: String,
            val imageUrl: String,
            val name: String,
            val category: String,
            val description: String,
            val prescription: List<String>,
            val prevention: List<String>,
            val __v: Int
        )
    }
}