package com.aston.astonintensivfinal.core.DBworker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aston.astonintensivfinal.AstonIntensivApplication
import java.util.Calendar

class DataBaseWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        try {


            val newsDao = AstonIntensivApplication.getAstonApplicationContext.newsDao

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