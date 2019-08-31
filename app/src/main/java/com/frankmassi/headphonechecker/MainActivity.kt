package com.frankmassi.headphonechecker

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private var headphoneStatusReceiver: HeadsetStatusReceiver? = null
    private val headsetFilter = IntentFilter(Intent.ACTION_HEADSET_PLUG)

    private fun updateTextView() {
        headphoneStatusReceiver!!.setTextView(findViewById<TextView>(R.id.activity_main_headphone_status))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        headphoneStatusReceiver = HeadsetStatusReceiver()
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
}
