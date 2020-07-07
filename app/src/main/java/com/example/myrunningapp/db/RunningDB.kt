package com.example.myrunningapp.db

import android.content.Context
import androidx.room.*
import java.lang.reflect.Type

@Database(entities = [RunModel::class],version = 1,exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class RunningDB : RoomDatabase(){

    abstract fun runDao() : RunDao
    
}