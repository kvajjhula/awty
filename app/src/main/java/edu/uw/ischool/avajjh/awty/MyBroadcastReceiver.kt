package edu.uw.ischool.avajjh.awty

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("MyBroadcastReceiver", "onReceive triggered")

        val message = intent?.getStringExtra("message")
        val phoneNumber = intent?.getStringExtra("phone")
        Toast.makeText(context, "($phoneNumber): $message", Toast.LENGTH_SHORT).show()
    }
}
