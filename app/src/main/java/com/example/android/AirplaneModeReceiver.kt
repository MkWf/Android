package com.example.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneMode = intent?.getBooleanExtra("state", false) ?: return

        if(isAirplaneMode){
            Toast.makeText(context, "Airplane mode on", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, "Airplane mode off", Toast.LENGTH_LONG).show()
        }
    }
}