package com.hongka.smsretriever.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hongka.smsretriever.data.Task

/**
 * Created by jusung.kim@sk.com on 2019/02/12
 */
class TasksViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()

    // mutableLiveData를 immutable 하게 노출
    val tasks: LiveData<List<Task>> get() = _tasks

    fun loadData() {
        val list = listOf(
            Task("15440988", "01020159625", null),
            Task("15770909", "01020159625", "우리(1303)"),
            Task("15331004", "01094490554", "국민은행")
        )
        _tasks.value = list
    }

    fun add(model: Task) {

    }

    fun update(model: Task) {

    }

    fun delete(model: Task) {

    }

    override fun onCleared() {
        super.onCleared()
    }
}