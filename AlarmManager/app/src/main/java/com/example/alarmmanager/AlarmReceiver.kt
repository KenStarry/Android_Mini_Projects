package com.example.alarmmanager

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context, DestinationActivity::class.java)
        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent?.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        //  Create a notification builder
        val builder = NotificationCompat.Builder(context!!, "KenstarryAlarm")
            .setSmallIcon(R.drawable.ic_task)
            .setContentTitle("Time to wake up!")
            .setContentText("it's now time to wake up my guy. Don't be lazy")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())
    }
}




















