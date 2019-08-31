package com.frankmassi.headphonechecker

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private var headphoneStatusReceiver: HeadphoneStatusIntentReceiver? = null
    private val headsetFilter = IntentFilter(Intent.ACTION_HEADSET_PLUG)

    private fun updateTextView() {
        findViewById<TextView>(R.id.activity_main_headphone_status).text =
            headphoneStatusReceiver!!.getStatusText()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        headphoneStatusReceiver = HeadphoneStatusIntentReceiver()
        registerReceiver(headphoneStatusReceiver, headsetFilter)
        updateTextView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(headphoneStatusReceiver)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(headphoneStatusReceiver, headsetFilter)
        updateTextView()
    }

    inner class HeadphoneStatusIntentReceiver : HeadsetStatusReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            super.onReceive(context, intent)
            if (intent.action == Intent.ACTION_HEADSET_PLUG) {
                updateTextView()
            }
        }
    }
}
