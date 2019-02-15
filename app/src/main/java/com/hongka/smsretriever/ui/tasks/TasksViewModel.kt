package com.hongka.smsretriever.ui.tasks

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hongka.smsretriever.R
import com.hongka.smsretriever.common.SingleLiveEvent
import com.hongka.smsretriever.data.Task
import com.hongka.smsretriever.data.source.local.ToDoDatabase
import com.hongka.smsretriever.ui.taskdetail.TaskDetailActivity
import com.hongka.smsretriever.ui.taskdetail.TaskDetailActivity.Companion.ADD_RESULT_OK
import com.hongka.smsretriever.ui.taskdetail.TaskDetailActivity.Companion.DELETE_RESULT_OK
import com.hongka.smsretriever.ui.taskdetail.TaskDetailActivity.Companion.EDIT_RESULT_OK

/**
 * Created by jusung.kim@sk.com on 2019/02/12
 */
class TasksViewModel : ViewModel() {
    private var toDoDatabase: ToDoDatabase? = null

    private val _tasks = MutableLiveData<List<Task>>()
    private val _snackbarMessage = SingleLiveEvent<Int>()
    internal val openTaskEvent = SingleLiveEvent<String>()

    // mutableLiveData를 immutable 하게 노출
    val tasks: LiveData<List<Task>> get() = _tasks
    val snackbarMessage: LiveData<Int> get() = _snackbarMessage

    fun setDatabase(application: Application) {
        toDoDatabase = ToDoDatabase.getInstance(application.applicationContext)
    }

    fun loadData() {
        val r = Runnable {
            _tasks.postValue(toDoDatabase?.taskDao()?.getTasks())
        }

        val thread = Thread(r)
        thread.start()
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int) {
        if (TaskDetailActivity.REQUEST_CODE == requestCode) {
            _snackbarMessage.value =
                when (resultCode) {
                    EDIT_RESULT_OK ->
                        R.string.successfully_saved_task_message
                    ADD_RESULT_OK ->
                        R.string.successfully_added_task_message
                    DELETE_RESULT_OK ->
                        R.string.successfully_deleted_task_message
                    else -> return
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ToDoDatabase.destroyInstance()
    }
}