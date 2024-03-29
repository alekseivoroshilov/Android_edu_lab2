package com.example.continuewatch

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    //https://maxfad.ru/programmer/android/218-ispolzuem-sharedpreferences-dlya-android.html
    var sharedPrefer: SharedPreferences? = null
    var watch_active = true
    var backgroundThread = Thread {
        while (watch_active) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //backgroundThread.start()
    }

    override fun onResume() {
        super.onResume()
        sharedPrefer = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        secondsElapsed = sharedPrefer!!.getInt("secondsElapsed", 0)
        watch_active = true
        backgroundThread.start()

    }

    override fun onPause() {
        super.onPause();
        watch_active = false
        val editor = sharedPrefer!!.edit()
        editor.putInt("secondsElapsed", secondsElapsed)
        editor.apply()
    }

}
