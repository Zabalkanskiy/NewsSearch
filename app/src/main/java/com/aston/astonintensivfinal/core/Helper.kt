package com.aston.astonintensivfinal.core

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.core.DBworker.DataBaseWorker
import com.aston.astonintensivfinal.core.networkConnection.NetworkConnection
import com.aston.astonintensivfinal.common.presentation.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

const val WORKER = "WORKER"
const val WORKMANAGER = "WORKMANAGER"


fun MainActivity.startWorker(){
    val sharedPref = getSharedPreferences(WORKER, Context.MODE_PRIVATE)

    val workBool =  sharedPref.getBoolean(WORKMANAGER, false)
    if (!workBool){
        val workRequest = PeriodicWorkRequestBuilder<DataBaseWorker>(1, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
        val edit = sharedPref.edit()
        edit.putBoolean(WORKMANAGER, true)
        edit.apply()
    }
}

fun MainActivity.networkConnection(application: Application): LiveData<Boolean>{
return NetworkConnection(application)
}

fun Fragment.hasNetwork(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        // for other device how are able to connect with Ethernet
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        // for check internet over Bluetooth
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}


