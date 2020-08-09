package com.example.connectivitymonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT < 23) {
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
                val connectionType = activeNetwork?.type

                if (isConnected) {
                    text.text = when (connectionType) {
                        ConnectivityManager.TYPE_WIFI -> "Wifiに接続しています"
                        ConnectivityManager.TYPE_MOBILE -> "モバイル通信に接続しています"
                        else -> "その他のネットワークに接続しています"
                    }
                } else {
                    text.text = "インターネットに接続していません"
                }
            } else {
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                if (capabilities != null) {
                    text.text = when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                            "Wifiに接続しています"
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                            "モバイル通信に接続しています"
                        else -> "その他のネットワークに接続しています"
                    }
                } else {
                    text.text = "インターネットに接続していません"
                }
            }
        }
    }
}