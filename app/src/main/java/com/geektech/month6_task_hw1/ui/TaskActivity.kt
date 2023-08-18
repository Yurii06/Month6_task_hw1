package com.geektech.month6_task_hw1.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.geektech.month6_task_hw1.MainActivity
import com.geektech.month6_task_hw1.MainViewModel
import com.geektech.month6_task_hw1.databinding.ActivityTaskBinding
import com.geektech.month6_task_hw1.model.Task

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initClick()

    }

    private fun initClick() {
        binding.btnSave.setOnClickListener {
            val resultIntent = Intent()

            resultIntent.putExtra(MainActivity.TITLE_KEY, binding.etTitle.text.toString())
            resultIntent.putExtra(MainActivity.DESCRIPTION_KEY, binding.etDesc.text.toString())

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}