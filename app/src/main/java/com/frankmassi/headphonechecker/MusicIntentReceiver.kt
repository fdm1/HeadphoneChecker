package com.frankmassi.headphonechecker

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import android.widget.Toast

private val TAG = "MainActivity"

internal class MusicIntentReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_HEADSET_PLUG) {
            val state = intent.getIntExtra("state", -1)
            when (state) {
                0 -> {
                    val text = "Using speaker"
                    Log.d(TAG, text)
                    showHeadphoneStatus(context, text)
                }
                1 -> {
                    val text = "Using headphones"
                    Log.d(TAG, text)
                    showHeadphoneStatus(context, text)
                }
                else -> {
                    val text = "I have no idea what the headphone state is"
                    Log.d(TAG, text)
                    showHeadphoneStatus(context, text)
                }
            }
        }
    }

    fun showHeadphoneStatus(context: Context, text: String) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }
}