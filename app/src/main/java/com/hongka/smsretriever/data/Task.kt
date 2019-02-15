package com.hongka.smsretriever.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by jusung.kim@sk.com on 2019/02/12
 */
@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "scNumber") var scNumber: String? = null,
    @ColumnInfo(name = "destinationNumber") var destinationNumber: String? = null,
    @ColumnInfo(name = "filterString") var filterString: String? = null,
    @ColumnInfo(name = "completed") var isCompleted: Boolean = false,
    @PrimaryKey @ColumnInfo(name = "entryid") val taskId: String = UUID.randomUUID().toString()
) {
    val isActive
        get() = !isCompleted
}