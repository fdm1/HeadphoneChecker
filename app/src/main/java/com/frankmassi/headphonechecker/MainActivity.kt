package com.frankmassi.headphonechecker

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var headphoneStatusReceiver: HeadphoneStatusIntentReceiver? = null
    private var chargingStatusReceiver: ChargingStatusReceiver? = null
    val headsetFilter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
    val chargingFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)

    val headphoneTextView: TextView = findViewById(R.id.activity_main_headphone_status)
    val chargingTextView: TextView = findViewById(R.id.activity_main_charging_status)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        headphoneStatusReceiver = HeadphoneStatusIntentReceiver()
        chargingStatusReceiver = ChargingStatusReceiver()
        registerReceiver(headphoneStatusReceiver, headsetFilter)
        registerReceiver(chargingStatusReceiver, chargingFilter)

        findViewById<TextView>(R.id.activity_main_headphone_status)
            .setText(headphoneStatusReceiver!!.getStatusText())
        findViewById<TextView>(R.id.activity_main_charging_status)
            .setText(chargingStatusReceiver!!.getStatusText())
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(headphoneStatusReceiver)
        unregisterReceiver(chargingStatusReceiver)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(headphoneStatusReceiver, headsetFilter)
        registerReceiver(chargingStatusReceiver, chargingFilter)

        findViewById<TextView>(R.id.activity_main_headphone_status)
            .setText(headphoneStatusReceiver!!.getStatusText())
        findViewById<TextView>(R.id.activity_main_charging_status)
            .setText(chargingStatusReceiver!!.getStatusText())
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
