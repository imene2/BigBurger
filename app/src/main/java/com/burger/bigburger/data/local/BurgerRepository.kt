package com.burger.bigburger.data.local

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class BurgerRepository(private val burgerDao: BurgerDao) {

    private val burgersList: LiveData<List<BurgerEntity>>

    init {
        burgersList = burgerDao.getList()
    }

    fun getBurgersList(): LiveData<List<BurgerEntity>> {
        return burgersList;
    }


    fun getTotal() : LiveData<Int>{
        return burgerDao.getTotal()
    }


    @WorkerThread
    suspend fun upsert(burgerEntity: BurgerEntity) {

        if (burgerDao.isExist(burgerEntity.ref) > 0) {

            val oldBurger = burgerDao.getByRef(burgerEntity.ref)
            val quantity = burgerEntity.quantity + oldBurger.quantity

            burgerEntity.quantity = quantity
            burgerEntity.price = burgerEntity.price * quantity

            burgerDao.update(burgerEntity)
        } else {
            burgerEntity.price = burgerEntity.quantity * burgerEntity.price
            burgerDao.insert(burgerEntity)
        }

    }


    @WorkerThread
    suspend fun delete(burgerEntity: BurgerEntity) {
        burgerDao.delete(burgerEntity)
    }

    @WorkerThread
    suspend fun deleteBasket() {
        burgerDao.deleteBasket()
    }






}