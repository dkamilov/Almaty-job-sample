package com.android.damir.jobfind.network

import android.content.Context
import android.net.ConnectivityManager

fun isNetworkAvailable(context: Context): Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        capabilities != null
    }else{
        val activeNetwork = connectivityManager.activeNetworkInfo
        activeNetwork?.isConnected == true
    }
}