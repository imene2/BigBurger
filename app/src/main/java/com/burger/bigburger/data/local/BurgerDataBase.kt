package com.burger.bigburger.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.experimental.CoroutineScope


@Database(entities = arrayOf(BurgerEntity::class), version = 1, exportSchema = false)
public abstract class BurgerDataBase : RoomDatabase() {

    abstract fun burgerDao(): BurgerDao

    companion object {


        @Volatile
        private var instance: BurgerDataBase? = null


        fun getInstance(context: Context): BurgerDataBase {


            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BurgerDataBase::class.java, "burger_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                }
                return instance as BurgerDataBase
            }

//            if(instance == null){
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    BurgerDataBase::class.java,"burger_db")
//                    .fallbackToDestructiveMigration()
//                    .build()
//
//            }
//            return instance

//            return instance ?: synchronized(this){
//                val INSTANCE =  Room.databaseBuilder(context.applicationContext,
//                    BurgerDataBase::class.java,"burger_db")
//                    .build()
//
//                instance = INSTANCE
//                instance
//            }!!
        }

    }

}