package com.example.alarmmanager

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var currentTimeText: TextView
    private lateinit var selectTime: Button
    private lateinit var setAlarm: Button
    private lateinit var cancelAlarm: Button
    private lateinit var timePicker: MaterialTimePicker

    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        currentTimeText = findViewById(R.id.currentTimeText)
        selectTime = findViewById(R.id.selectTimeBtn)
        setAlarm = findViewById(R.id.setAlarmBtn)
        cancelAlarm = findViewById(R.id.cancelAlarmBtn)

        selectTime.setOnClickListener {
            showTimePicker()
        }

        setAlarm.setOnClickListener {
            setMyAlarm()
        }

        cancelAlarm.setOnClickListener {

            cancelMyAlarm()
        }

    }

    private fun cancelMyAlarm() {

        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        if (alarmManager == null)
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm cancelled!", Toast.LENGTH_SHORT).show()
    }

    private fun setMyAlarm() {

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showTimePicker() {

        calendar = Calendar.getInstance()

        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set Alarm")
            .setPositiveButtonText("set")
            .build()
        timePicker.show(supportFragmentManager, "KenStarry")

        timePicker.addOnPositiveButtonClickListener {
            if (timePicker.hour > 12) {
                currentTimeText.text = String.format(
                    "%02d",
                    "${timePicker.hour + 12} : ${String.format("%02d", timePicker.minute)} : PM"
                )
            } else {
                currentTimeText.text = "${timePicker.hour} : ${timePicker.minute} : AM"
            }

            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }

    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "KenstarryAlarmChannel"
            val description = "Channel for Alarm"
            val importance = NotificationManager.IMPORTANCE_HIGH

            //  The id of the channel should match with what you have set in your AlarmReceiver
            val channel = NotificationChannel("KenstarryAlarm", name, importance)
            channel.description = description

            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}




















