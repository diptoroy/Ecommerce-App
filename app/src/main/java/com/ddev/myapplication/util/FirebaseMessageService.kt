package com.ddev.myapplication.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavDeepLinkBuilder
import com.ddev.myapplication.Application.EcommerceApp
import com.ddev.myapplication.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage



class FirebaseMessageService: FirebaseMessagingService() {

        private val FCM_CHANNEL_ID = "Firebase Notification Channel"
        private val FCM_NOTIFICATION_ID = 102

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        sendNotification(message.notification!!.title!!,message.notification!!.body!!)
    }

    private fun sendNotification(title: String,messageBody: String){
        val pendingIntent = NavDeepLinkBuilder(EcommerceApp.getApp()!!.applicationContext)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.homePageFragment)
            .createPendingIntent()

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, FCM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                FCM_CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(FCM_NOTIFICATION_ID, notificationBuilder.build())
    }
}