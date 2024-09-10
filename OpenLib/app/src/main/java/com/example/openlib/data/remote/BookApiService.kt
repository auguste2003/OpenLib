package com.example.openlib.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {
    @GET("search.json")
  suspend  fun searchbooks(@Query("q") query : String , @Query("limit") limit:Int =10
    ):Response<SearchResponse> // limite á 10 livres

}