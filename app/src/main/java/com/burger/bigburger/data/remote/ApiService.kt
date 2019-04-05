package com.burger.bigburger.data.remote


import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {


    @GET("catalog")
    fun getListBurgers(): Observable<ArrayList<Burger>>


}