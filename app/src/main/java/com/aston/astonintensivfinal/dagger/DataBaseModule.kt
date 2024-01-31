package com.aston.astonintensivfinal.dagger

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aston.astonintensivfinal.core.data.databaseNews.DatabaseNews
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataBaseModule {

    @Singleton
    @Provides
    fun provideRoomDataBase(context: Context): DatabaseNews {
        return Room.databaseBuilder(context, DatabaseNews::class.java, "news_db")
            .fallbackToDestructiveMigration().build()

    }

    @Singleton
    @Provides
    fun provideNewsDao(dataBase: DatabaseNews): NewsDao {
        return dataBase.getNewsModelDao()
    }
}