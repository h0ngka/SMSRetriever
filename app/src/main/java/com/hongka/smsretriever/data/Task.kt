package com.hongka.smsretriever.data

/**
 * Created by jusung.kim@sk.com on 2019/02/12
 */
data class Task(
    val scNumber: String,
    val destinationNumber: String,
    val filterString: String? = null
)