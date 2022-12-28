package com.omarbashawith.mufeed_app.features.list.domain

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.omarbashawith.mufeed_app.MainActivity
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.features.list.data.FcmToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class FcmService : FirebaseMessagingService() {
    @Inject
    lateinit var repo: PostRepo
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onNewToken(token: String) {
        Log.d("FCM_TOKEN", token)
        ioScope.launch {
            repo.sendToken(FcmToken(token))
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        val notification = message.notification
        val title = notification?.title
        val desc = notification?.body
        val image = notification?.icon
        showNotification(title, desc, image)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNotification(
        title: String?,
        desc: String?,
        image: String?
    ) {
        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
        )
        val bitmap = BitmapFactory.decodeStream(URL(image).openStream())
        val notification = NotificationCompat.Builder(this,"New Post Notification")
            .setSmallIcon(R.drawable.ic_android)
            .setLargeIcon(bitmap)
            .setContentTitle(title)
            .setContentText(desc)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0,notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.cancel()
    }
}