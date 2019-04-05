package com.burger.bigburger.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface BurgerDao {


    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(burgerEntity: BurgerEntity)

    @Update(onConflict =  OnConflictStrategy.IGNORE)
    fun update(burgerEntity: BurgerEntity)

    @Delete
    fun delete(burgerEntity: BurgerEntity)

    @Query("DELETE FROM burger_table")
    fun deleteBasket()

    @Query("SELECT * FROM burger_table WHERE ref= :ref LIMIT 1")
    fun getByRef(ref : String) : BurgerEntity

    @Query("SELECT COUNT(*) FROM burger_table WHERE ref= :ref LIMIT 1")
    fun isExist(ref : String) : Int

    @Query("SELECT * FROM burger_table")
    fun getList() : LiveData<List<BurgerEntity>>

    @Query("SELECT SUM(price) as total FROM burger_table")
    fun getTotal() : LiveData<Int>

}