package com.search.news.core.DBworker

import android.content.Context
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.search.news.SearchApplication
import com.search.news.core.data.databaseNews.DatabaseNews
import com.search.news.core.data.databaseNews.NewsModel.NewsDao
import java.util.Calendar

class DataBaseWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        try {


            val newsDao: NewsDao =
                Room.databaseBuilder(applicationContext, DatabaseNews::class.java, "news_db")
                    .fallbackToDestructiveMigration().build().getNewsModelDao()
            val calendar = Calendar.getInstance()

            calendar.add(Calendar.DAY_OF_YEAR, -14)

            val twoWeekAgo = calendar.time

            newsDao.deleteNewsOlderThanTwoWeeks(twoWeekAgo)
            return Result.success()
        } catch (e: Exception) {

            return Result.retry()
        }


    }
}