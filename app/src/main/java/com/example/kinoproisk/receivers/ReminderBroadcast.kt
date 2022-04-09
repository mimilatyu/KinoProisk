package com.example.kinoproisk.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.view.notification.NotificationConstants
import com.example.kinoproisk.view.notification.NotificationHelper

class ReminderBroadcast: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.getBundleExtra(NotificationConstants.FILM_BUNDLE_KEY)
        val film: Film = bundle?.get(NotificationConstants.FILM_KEY) as Film
        NotificationHelper.createNotification(context!!, film)
    }
}