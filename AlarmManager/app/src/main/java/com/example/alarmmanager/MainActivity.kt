package com.example.alarmmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.getSystemService
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class MainActivity : AppCompatActivity() {

    private lateinit var selectTime: Button
    private lateinit var setAlarm: Button
    private lateinit var cancelAlarm: Button
    private lateinit var timePicker: MaterialTimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        selectTime = findViewById(R.id.selectTimeBtn)
        setAlarm = findViewById(R.id.setAlarmBtn)
        cancelAlarm = findViewById(R.id.cancelAlarmBtn)

        selectTime.setOnClickListener {
            showTimePicker()
        }

        setAlarm.setOnClickListener {

        }

        cancelAlarm.setOnClickListener {

        }

    }

    private fun showTimePicker() {

        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set Alarm")
            .build()

    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "KenstarryAlarmChannel"
            val description = "Channel for Alarm"
            val importance = NotificationManager.IMPORTANCE_HIGH

            //  The id of the channel should match with what you have set in your AlarmReceiver
            val channel = NotificationChannel("KenstarryAlarm", name, importance)
            channel.description = description

            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}




















