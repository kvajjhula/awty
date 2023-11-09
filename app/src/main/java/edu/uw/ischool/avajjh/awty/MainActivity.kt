package edu.uw.ischool.avajjh.awty

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var messageText: EditText
    private lateinit var phoneText: EditText
    private lateinit var numText: EditText
    private lateinit var startButton: Button
    private var isServiceRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageText = findViewById(R.id.message_editText)
        phoneText = findViewById(R.id.phone_editText)
        numText = findViewById(R.id.num_editText)
        startButton = findViewById(R.id.startButton)
        startButton.isEnabled = false

        messageText.addTextChangedListener(textWatcher)
        phoneText.addTextChangedListener(textWatcher)
        numText.addTextChangedListener(textWatcher)

        startButton.setOnClickListener() {
            isServiceRunning = !isServiceRunning

            val intent = Intent(this, MyBroadcastReceiver::class.java)
            intent.putExtra("message", messageText.text.toString())
            intent.putExtra("phone", phoneText.text.toString())
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val alarmManager : AlarmManager =  getSystemService(Context.ALARM_SERVICE) as AlarmManager


            if (isServiceRunning) {
                startButton.text = "Stop"
                val intervalMillis = numText.text.toString().toLong() * 60 * 1000
                Log.i("tag", "isServiceRunning reached")

                // Schedule the alarm
                alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + intervalMillis,
                    intervalMillis,
                    pendingIntent
                )

            } else {
                startButton.text = "Start"
                alarmManager.cancel(pendingIntent)
            }
        }
    }


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val allFieldsFilled = (messageText.text.isNotEmpty()) &&
                    (phoneText.text.isNotEmpty()) &&
                    (numText.text.isNotEmpty()) &&
                    ((numText.text.toString().toIntOrNull() ?: 0) > 0)
            startButton.isEnabled = allFieldsFilled

        }
    }
}