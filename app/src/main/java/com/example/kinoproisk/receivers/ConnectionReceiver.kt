package com.example.kinoproisk.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.widget.Toast
import com.example.kinoproisk.R

class ConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if (intent == null) return
        when(intent.action) {
                Intent.ACTION_BATTERY_LOW -> Toast.makeText(context, R.string.battery_low, Toast.LENGTH_SHORT).show()
                Intent.ACTION_POWER_CONNECTED -> Toast.makeText(context, R.string.power_connected, Toast.LENGTH_SHORT).show()
        }
    }
}