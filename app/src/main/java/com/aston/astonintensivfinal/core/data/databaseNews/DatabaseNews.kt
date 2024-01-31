package com.aston.astonintensivfinal.core.data.databaseNews

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.BooleanTypeConverter
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.DateTypeConverters
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsEverythikModelEntity
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.SourceNewsModelEntity

const val VERSION_DATABASE = 16
@Database(entities = [NewsModelEntity::class, SourceNewsModelEntity::class, NewsEverythikModelEntity::class],
    version = VERSION_DATABASE )
@TypeConverters(DateTypeConverters::class, BooleanTypeConverter::class)
abstract class DatabaseNews : RoomDatabase() {
    abstract fun getNewsModelDao(): NewsDao

}