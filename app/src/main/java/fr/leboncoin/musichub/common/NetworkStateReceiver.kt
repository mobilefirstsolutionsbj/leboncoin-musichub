package fr.leboncoin.musichub.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkStateReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (networkStateListener != null) {
            networkStateListener?.onNetworkStateChanged(isConnectedOrConnecting(context!!))
        }
    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    interface NetworkStateListener {
        fun onNetworkStateChanged(isConnected: Boolean)
    }

    companion object {
        var networkStateListener: NetworkStateListener? = null
    }
}
