package com.burger.bigburger.viewmodel.basket

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.burger.bigburger.data.local.BurgerDataBase
import com.burger.bigburger.data.local.BurgerEntity
import com.burger.bigburger.data.local.BurgerRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class BasketViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)


    private val repository: BurgerRepository


    init {
        val burgerDao = BurgerDataBase.getInstance(application).burgerDao()
        repository = BurgerRepository(burgerDao)
    }

    /**
     * insert item if not exist, else update current
     */
    fun upsert(burgerEntity: BurgerEntity) = scope.launch(Dispatchers.IO) {
        repository.upsert(burgerEntity)
    }

    /**
     *delete entity
     */
    fun delete(burgerEntity: BurgerEntity) = scope.launch(Dispatchers.IO) {
        repository.delete(burgerEntity)
    }

    /**
     * delete all entities
     */
    fun deleteBasket() = scope.launch(Dispatchers.IO) {
        repository.deleteBasket()
    }

    /**
     * get list entities
     */
    fun getBasket(): LiveData<List<BurgerEntity>> {
        return repository.getBurgersList()
    }

    /**
     * get total price
     */
    fun getTotal(): LiveData<Int> {
        return repository.getTotal()
    }


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}