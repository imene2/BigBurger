package com.burger.bigburger.viewmodel.burgerslist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.burger.bigburger.data.remote.Burger
import com.burger.bigburger.data.remote.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {


    val isLoading = MutableLiveData<Boolean>().apply { postValue(true) }
    val isLoadingFailed = MutableLiveData<Boolean>().apply { postValue(false) }


    private val list = MutableLiveData<List<Burger>>()


    /**
     * get list burgers from server
     */
    fun getBurgers(): MutableLiveData<List<Burger>> {

        isLoading.value = true

        RetrofitInstance.retrofitInstance(this.getApplication())
            .getListBurgers()
            .enqueue(object : Callback<List<Burger>> {

                override fun onResponse(call: Call<List<Burger>>, response: Response<List<Burger>>) {
                    if (response.body() != null) {

                        isLoading.value = false
                        isLoadingFailed.value = false
                        list.value = response.body()

                    }
                }

                override fun onFailure(call: Call<List<Burger>>, t: Throwable) {
                    isLoading.value = false
                    isLoadingFailed.value = true
                }
            })

        return list
    }


    /**
     * is data loading
     */
    fun isLoading(): LiveData<Boolean> {

        return isLoading
    }

    /**
     * check if loading data from server failed
     */
    fun isLoadingFailed(): LiveData<Boolean> {

        return isLoadingFailed
    }
}