package com.example.android

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    var receiver: AirplaneModeReceiver = AirplaneModeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Register receiver
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also{
            registerReceiver(receiver, it)
        }
    }

    override fun onStop() {
        super.onStop()

        //Unregister receiver
        unregisterReceiver(receiver)
    }
}