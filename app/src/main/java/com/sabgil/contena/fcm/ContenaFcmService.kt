package com.sabgil.contena.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sabgil.contena.R
import com.sabgil.contena.presenter.splash.SplashActivity

class ContenaFcmService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val intent = Intent(this, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, 0
        )

        val builder = NotificationCompat.Builder(
            this, NEW_PRODUCT_NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark) // TODO : must be changed to app icon
            .setContentTitle(
                remoteMessage.notification?.title
                    ?: getString(R.string.fcm_notification_default_title)
            )
            .setContentText(
                remoteMessage.notification?.body
                    ?: getString(R.string.fcm_notification_default_body)
            )
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager =
            NotificationManagerCompat.from(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NEW_PRODUCT_NOTIFICATION_CHANNEL_ID,
                "신상품 알림",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NEW_PRODUCT_NOTIFICATION_ID, builder.build())
    }

    companion object {
        private const val NEW_PRODUCT_NOTIFICATION_CHANNEL_ID =
            "NEW_PRODUCT_NOTIFICATION_CHANNEL_ID"

        private const val NEW_PRODUCT_NOTIFICATION_ID = 0
    }
}