package com.example.myrunningapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(runModel: RunModel)

    @Delete
    suspend fun deleteRun(runModel: RunModel)

    @Query("SELECT * FROM runningtable ORDER BY timeStamp DESC")
    fun getRunsSortedByDate() : LiveData<List<RunModel>>

    @Query("SELECT * FROM runningtable ORDER BY avgSpeed ")
    fun getRunsSortedByAvgSpeed() : LiveData<List<RunModel>>

    @Query("SELECT * FROM runningtable ORDER BY disntanceInMeters")
    fun getRunsSortedByDistanceInMeters() :  LiveData<List<RunModel>>

    @Query("SELECT * FROM runningtable ORDER BY timeInMillis")
    fun getRunsSortedByTimeInMillis() : LiveData<List<RunModel>>

    @Query("SELECT * FROM runningtable ORDER BY caloriesBurned")
    fun getRunsSortedByCaloriesBurned() : LiveData<List<RunModel>>


    ///Totals
    @Query("SELECT SUM(timeStamp) FROM runningtable")
    fun getTotalTimeStamp() : LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM runningtable")
    fun getTotalCaloriesBurned() : LiveData<Int>

    @Query("SELECT SUM(timeInMillis) FROM runningtable")
    fun getTotalTimeInMillis() : LiveData<Long>

    @Query("SELECT AVG(avgSpeed) FROM runningtable")
    fun getTotalAvgSpeeds() : LiveData<Float>
}