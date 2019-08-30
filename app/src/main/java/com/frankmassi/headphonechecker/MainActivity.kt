package com.frankmassi.headphonechecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private var headphoneStatusReceiver: HeadphoneStatusIntentReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupReceivers()
        updateTextViews()
    }

    private fun setupReceivers() {
        val headsetFilter = IntentFilter(Intent.ACTION_HEADSET_PLUG)

        if (headphoneStatusReceiver == null) {
            headphoneStatusReceiver = HeadphoneStatusIntentReceiver()
        }

        registerReceiver(headphoneStatusReceiver, headsetFilter)
    }

    private fun teardownReceivers() {
        unregisterReceiver(headphoneStatusReceiver)
    }

    private fun updateTextViews() {
        val headphoneView: TextView = findViewById(R.id.activity_main_headphone_status)
        headphoneView.text = headphoneStatusReceiver!!.getStatusText()
    }

    override fun onDestroy() {
        super.onDestroy()
        teardownReceivers()
    }

    override fun onResume() {
        super.onResume()
        setupReceivers()
        updateTextViews()
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
            if(intent.action == Intent.ACTION_HEADSET_PLUG) {
                val state = parseHeadsetPlugState(intent, context)
                setStatusText(state)
                //      TODO: make a setting option for show toast
                updateTextViews()
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
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
