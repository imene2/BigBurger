package com.burger.bigburger.data.remote


import retrofit2.Call
import retrofit2.http.GET

interface ApiService {


    @GET("catalog")
    fun getListBurgers(): Call<List<Burger>>


}