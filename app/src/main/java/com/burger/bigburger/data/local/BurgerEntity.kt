package com.burger.bigburger.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "burger_table")
data class BurgerEntity(@PrimaryKey @NonNull var ref : String,
                        var title : String,
                        var description : String,
                        var thumbnail : String,
                        var price : Int,
                        var quantity : Int) {

}