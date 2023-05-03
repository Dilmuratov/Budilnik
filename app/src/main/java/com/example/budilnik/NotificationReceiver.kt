package com.example.budilnik

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.budilnik.ui.activities.AlarmActivity
import com.example.budilnik.ui.activities.MainActivity
import kotlin.random.Random

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val intent = Intent(context, MainActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_ONE_SHOT
            )

            val forFullScreenIntent = Intent(context, AlarmActivity::class.java)

            val forFullScreenPendingIntent = PendingIntent.getActivity(
                context,
                0,
                forFullScreenIntent,
                PendingIntent.FLAG_MUTABLE
                )

            val notification = NotificationCompat.Builder(context, "Texnopos-7117")
                .setSmallIcon(R.drawable.ic_alarm)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText("This is desription of Notification")
                .setOngoing(true)
                .setFullScreenIntent(forFullScreenPendingIntent, true)
                .build()

            val notificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        "Texnopos-7117",
                        "Alarm shigariw",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                )

                Log.d("Receiver", "If tin ishinde")
            }
            notificationManager.notify("TTTT", Random.nextInt(1, 100), notification)


        }
    }
}