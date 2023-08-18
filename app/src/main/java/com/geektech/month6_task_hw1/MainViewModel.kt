package com.geektech.month6_task_hw1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geektech.month6_task_hw1.model.Task

class MainViewModel : ViewModel() {

    private var tasks = mutableListOf<Task>()
    private var _list = MutableLiveData<MutableList<Task>>()
    val list: LiveData<MutableList<Task>> = _list

    fun addTask(task: Task) {
        tasks.add(task)
        _list.value = tasks
        Log.e("kiber", list.value.toString())
    }

    fun deleteTask(task: Task) {
        tasks.remove(task)
        _list.value = tasks
    }

    fun checkedTask(task: Task, isChecked: Boolean){
        tasks.replaceAll {
            if (it == task) {
                it.checkBox = isChecked
            }
            Log.e("kiber", list.value.toString())
            it
        }
    }
}