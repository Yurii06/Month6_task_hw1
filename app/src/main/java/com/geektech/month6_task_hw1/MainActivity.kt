package com.geektech.month6_task_hw1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.geektech.month6_task_hw1.databinding.ActivityMainBinding
import com.geektech.month6_task_hw1.model.Task
import com.geektech.month6_task_hw1.ui.TaskActivity
import com.geektech.month6_task_hw1.ui.TaskAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = TaskAdapter(this::onLongClickTask, this::isCheckedTask)

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerView.adapter = adapter

        // Инициализируем resultLauncher с использованием registerForActivityResult
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val dataIntent: Intent? = result.data
                    // Вызываем функцию для обработки результата
                    handleActivityResult(dataIntent)
                }
            }

        initView()
        initClick()
    }

    private fun initClick() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun handleActivityResult(intent: Intent?) { // функция для оброботки результата (из TaskActivity)
        if (intent != null) {
            val getTitle = intent.getStringExtra(TITLE_KEY)
            val getDescription = intent.getStringExtra(DESCRIPTION_KEY)
            if (getTitle != null) {
                val data = Task(
                    title = getTitle,
                    description = getDescription,
                    checkBox = false
                )
                viewModel.addTask(data)
            }
        }
    }

    private fun initView() {
        viewModel.list.observe(this) { updatedList ->
            adapter.setList(updatedList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun onLongClickTask(task: Task) { // deleteTaskClick
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Вы хотите удалить данные?")
            .setMessage("Востановить данные бедет невозможным!")
            .setPositiveButton("ОК") { dialog: DialogInterface, _: Int ->
                viewModel.deleteTask(task)
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        dialogBuilder.show()
    }

    private fun isCheckedTask(task: Task, isChecked: Boolean) {
        viewModel.checkedTask(task,isChecked)
    }
    companion object{
        const val TITLE_KEY ="title.key"
        const val DESCRIPTION_KEY ="description.key"
    }
}