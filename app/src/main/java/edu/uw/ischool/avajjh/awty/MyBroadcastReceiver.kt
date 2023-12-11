package edu.uw.ischool.avajjh.awty

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("MyBroadcastReceiver", "onReceive triggered")

        val message = intent?.getStringExtra("message")
        val phoneNumber = intent?.getStringExtra("phone")

        if (context != null && phoneNumber != null && message != null) {
            try {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
