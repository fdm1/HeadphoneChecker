package com.frankmassi.headphonechecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast


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

    inner class HeadphoneStatusIntentReceiver : BroadcastReceiver() {

        private var statusText: String? = null

        private fun setStatusText(text: String) {
            statusText = text
        }

        fun getStatusText(): String? {
            return statusText
        }

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_HEADSET_PLUG) {
                val state = parseHeadsetPlugState(intent, context)
                setStatusText(state)
                //      TODO: make a setting option for show toast
                updateTextView()
                showHeadphoneStatusToast(context)
            }
        }

        private fun parseHeadsetPlugState(intent: Intent, context: Context): String {
            return when (intent.getIntExtra("state", -1)) {
                0 -> context.getString(R.string.using_speaker)
                1 -> context.getString(R.string.using_headphones)
                else -> context.getString(R.string.using_unknown)
            }
        }

        fun showHeadphoneStatusToast(context: Context) {
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, getStatusText(), duration)
            toast.show()
        }
    }
}
