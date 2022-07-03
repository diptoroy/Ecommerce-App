package com.ddev.myapplication.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.ddev.myapplication.R
import com.ddev.myapplication.view.activiry.MainActivity

object Notification {
    private const val CHANNEL_ID = "Notification Channel"
    private const val NOTIFICATION_ID = 101

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val title = "notification"
            var description = "notification desc"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel:NotificationChannel = NotificationChannel(CHANNEL_ID,title,importance).apply {
                description = description
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun buildNotification(context: Context,title: String,contentText: String,intent: PendingIntent){

        val builder = NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(intent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)){
            notify(NOTIFICATION_ID,builder.build())
        }
    }
}