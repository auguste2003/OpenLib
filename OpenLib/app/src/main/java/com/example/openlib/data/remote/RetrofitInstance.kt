package com.example.openlib.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{

    private const val baseUrl = "https://openlibrary.org/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val bookAPI :BookApiService= getInstance().create(BookApiService::class.java)
}