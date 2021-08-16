package com.hongka.smsretriever

import android.app.IntentService
import android.content.Context
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

        fun makeIntent(context: Context, sender: String, contents: String): Intent {
            return Intent(context, SMSSenderService::class.java)
                .apply {
                    putExtra("sender", sender)
                    putExtra("contents", contents)
                }
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        intent ?: return

        val sender = intent.extras?.get("sender") as String
        val contents = intent.extras?.get("contents") as String

        smsSend(sender, contents)
    }

    private fun smsSend(sender: String, contents: String) {
        Log.i(TAG, "SMS 전송 완료")
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(DESTINATION_ADDRESS, sender, contents, null, null)
    }
}