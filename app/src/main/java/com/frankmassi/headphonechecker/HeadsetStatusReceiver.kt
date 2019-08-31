package com.frankmassi.headphonechecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

open class HeadsetStatusReceiver : BroadcastReceiver() {

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

    private fun showHeadphoneStatusToast(context: Context) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, getStatusText(), duration)
        toast.show()
    }
}
