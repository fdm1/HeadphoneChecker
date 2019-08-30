package com.frankmassi.headphonechecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

private const val TAG = "MainActivity"
class HeadphoneStatusIntentReceiver : BroadcastReceiver() {

    private var statusText: String? = null

    private fun setStatusText(context: Context, text: String) {
        statusText = text
        Log.d(TAG, text)
//      TODO: make a setting option for show toast
        showHeadphoneStatusToast(context)
    }

    fun getStatusText(): String? {
        return statusText
    }

    override fun onReceive(context: Context, intent: Intent) {
        parseHeadsetPlugState(intent, context)
    }

    private fun parseHeadsetPlugState(intent: Intent, context: Context) {
        when (intent.getIntExtra("state", -1)) {
            0 -> setStatusText(context, context.getString(R.string.using_speaker))
            1 -> setStatusText(context, context.getString(R.string.using_headphones))
            else -> setStatusText(context, context.getString(R.string.using_unknown))
        }
    }

    fun showHeadphoneStatusToast(context: Context) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, getStatusText(), duration)
        toast.show()
    }
}
