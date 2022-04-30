package com.example.kinoproisk.view.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.kinoproisk.R
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.receivers.ReminderBroadcast
import com.example.kinoproisk.view.MainActivity
import com.example.remote_module.entity.ApiConstants
import java.util.*

object NotificationHelper {
    fun createNotification(context: Context, film: Film) {
        val mIntent = Intent(context, MainActivity::class.java)
        mIntent.putExtra("film", film)
        val bundle = mIntent.extras
       // val pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.detailsFragment)
            .setArguments(bundle)
            .createPendingIntent()


        val builder = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_round_watch_later)
            setContentTitle("ВРЕМЯ СМОТРЕТЬ ЭТО!!!!")
            setContentText(film.title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        val notificationManager = NotificationManagerCompat.from(context)

        Glide.with(context)
            .asBitmap()
            .load(ApiConstants.IMAGES_URL + "w500" + film.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    notificationManager.notify(film.id, builder.build())
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
        notificationManager.notify(film.id, builder.build())
    }

    fun notificationSet(context: Context, film: Film) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(
            context,
            { _, dpdYear, dpdMonth, dayOfMonth ->
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, pickerMinute ->
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(
                          dpdYear,
                          dpdMonth,
                          dayOfMonth,
                          hourOfDay,
                          pickerMinute,
                            0
                        )
                        val dateTimeInMillis = pickedDateTime.timeInMillis
                        createWatchLaterEvent(context, dateTimeInMillis, film)
                    }
                TimePickerDialog(
                    context,
                    timeSetListener,
                    currentHour,
                    currentMinute,
                    true
                ).show()
            },
            currentYear,
            currentMonth,
            currentDay
        ).show()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createWatchLaterEvent(context: Context, dateTimeInMillis: Long, film: Film) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(film.title, null, context, ReminderBroadcast()::class.java)
        val bundle = Bundle()
        bundle.putParcelable(NotificationConstants.FILM_KEY, film)
        intent.putExtra(NotificationConstants.FILM_BUNDLE_KEY, bundle)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            dateTimeInMillis,
            pendingIntent
        )
    }
}