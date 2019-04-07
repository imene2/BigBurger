package com.burger.bigburger.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

@Entity(tableName = "burger_table")
data class BurgerEntity(@PrimaryKey @NonNull var ref : String,
                        var title : String,
                        var description : String,
                        var thumbnail : String,
                        var price : Int,
                        var quantity : Int) {

}