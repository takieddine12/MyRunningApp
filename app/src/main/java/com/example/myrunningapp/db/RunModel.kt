package com.example.myrunningapp.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "runningtable")
data class RunModel(
    var bitMap : Bitmap? = null,
    var timeStamp : Long = 0L, //this is when the run took place
    var avgSpeed : Float = 0F,
    var disntanceInMeters : Int = 0,
    var timeInMillis : Long = 0L,  //this is for how long the run was
    var caloriesBurned : Int = 0
) {

    @PrimaryKey(autoGenerate = true)
    @NotNull
     var runningID : Int? = null
}