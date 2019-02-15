package com.hongka.smsretriever.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hongka.smsretriever.data.Task

/**
 * Created by jusung.kim@sk.com on 2019/02/15
 */

/**
 * The Room Database that contains the Task table.
 */
@Database(entities = [Task::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao(): TasksDao

    companion object {
        private var INSTANCE: ToDoDatabase? = null

        fun getInstance(context: Context): ToDoDatabase? {
            if (INSTANCE == null) {
                synchronized(ToDoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoDatabase::class.java, "Tasks"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}