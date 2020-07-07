package com.example.myrunningapp.di

import com.example.myrunningapp.db.RunDao
import com.example.myrunningapp.db.RunModel
import javax.inject.Inject

class runRespository @Inject constructor(var runDao: RunDao) {


    suspend fun insertRun(runModel: RunModel) = runDao.insertRun(runModel)
    suspend fun deleteRun(runModel: RunModel) = runDao.deleteRun(runModel)
    fun runsSortedByDate() = runDao.getRunsSortedByDate()
    fun runsSortedByAvgSpeed() = runDao.getRunsSortedByAvgSpeed()
    fun runsSortedByDistanceInMeters() = runDao.getRunsSortedByDistanceInMeters()
    fun runsSortedByTimeInMillis() = runDao.getRunsSortedByTimeInMillis()
    fun runsSortedByCaloriesBurned() = runDao.getRunsSortedByCaloriesBurned()
    fun TotalTimeStamp() = runDao.getTotalTimeStamp()
    fun TotalCaloriesBurned() = runDao.getTotalCaloriesBurned()
    fun TotaTimeInMillis() = runDao.getTotalTimeInMillis()
    fun TotalAvgSpeeds() = runDao.getTotalAvgSpeeds()



}