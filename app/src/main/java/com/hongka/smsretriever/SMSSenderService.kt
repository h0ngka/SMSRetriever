package com.hongka.smsretriever

import android.app.IntentService
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log

/**
 * Created by jusung.kim@sk.com on 2019/02/02
 */

class SMSSenderService : IntentService("SMSSenderService") {
    companion object {
        private const val TAG = "SMSSenderService"
        private const val DESTINATION_ADDRESS = "01020159625"
        private const val SC_ADDRESS = "15889955"
    }

    override fun onHandleIntent(intent: Intent?) {
        val contents = intent?.extras?.get("contents") as String
        smsSend(contents)
    }

    private fun smsSend(contents: String) {
        Log.i(TAG, "SMS 전송 완료")
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(DESTINATION_ADDRESS, SC_ADDRESS, contents, null, null)
    }
}