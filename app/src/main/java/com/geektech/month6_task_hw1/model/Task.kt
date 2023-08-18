package com.geektech.month6_task_hw1.model


data class Task(
    val title: String? = null,
    val description: String? = null,
    var checkBox: Boolean = false,
)