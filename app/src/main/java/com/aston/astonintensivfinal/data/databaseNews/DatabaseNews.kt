package com.aston.astonintensivfinal.data.databaseNews

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsModelEntity

const val VERSION_DATABASE = 1
@Database(entities = [NewsModelEntity::class],
    version = VERSION_DATABASE )
abstract class DatabaseNews : RoomDatabase() {
    abstract fun getNewsModelDao(): NewsDao

}