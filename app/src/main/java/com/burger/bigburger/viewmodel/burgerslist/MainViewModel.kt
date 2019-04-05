package com.burger.bigburger.viewmodel.burgerslist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.burger.bigburger.data.remote.Burger
import com.burger.bigburger.data.remote.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var disposable: Disposable

    val isLoading = MutableLiveData<Boolean>().apply { postValue(true) }
    val isLoadingFailed = MutableLiveData<Boolean>().apply { postValue(false) }


    private val list = MutableLiveData<List<Burger>>()


    /**
     * get list burgers from server
     */
    fun getBurgers(): MutableLiveData<List<Burger>> {

        isLoading.value = true

        disposable = RetrofitInstance.retrofitInstance(this.getApplication())
            .getListBurgers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { burgers ->

                    isLoading.value = false
                    isLoadingFailed.value = false

                    list.value = burgers

                },
                { error ->
                    isLoading.value = false
                    isLoadingFailed.value = true
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