package com.hongka.smsretriever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import java.util.*

/**
 * Created by jusung.kim@sk.com on 2019/02/02
 */

class SMSReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "SMSReceiver"
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == SMS_RECEIVED) {
            Log.d(TAG, "onReceive() 호출")

            val bundle = intent.extras
            val messages = parseSmsMessage(bundle)

            if (messages.isNotEmpty()) {
                // 메세지의 내용을 가져옴
                val sender = messages[0]?.originatingAddress
                val contents = messages[0]?.messageBody
                val receivedDate = Date(messages[0]?.timestampMillis ?: 0)

                // 로그를 찍어보는 과정이므로 생략해도 됨
                Log.d(TAG, "sender : $sender")
                Log.d(TAG, "contents : $contents")
                Log.d(TAG, "receivedDate : $receivedDate")

                if (sender == "15889955") {
                    if (contents?.contains("우리(1804)") == true) {
                        val intent = Intent(context, SMSSenderService::class.java)
                        intent.putExtra("contents", contents)
                        context?.startService(intent)
                    }
                }
            }
        }
    }

    private fun parseSmsMessage(bundle: Bundle?): Array<SmsMessage?> {
        val objs = bundle?.get("pdus") as Array<*>
        val messages = arrayOfNulls<SmsMessage>(objs.size)

        for (i in objs.indices) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val format = bundle.getString("format")
                messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray, format)
            } else {
                @Suppress("DEPRECATION")
                messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray)
            }
        }

        return messages
    }
}