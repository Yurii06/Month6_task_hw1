package com.geektech.month6_task_hw1.ui


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.geektech.month6_task_hw1.databinding.ItemTaskBinding
import com.geektech.month6_task_hw1.model.Task

class TaskAdapter(
    private val onLongClickTask: (Task) -> Unit, // deleteTaskClick
    private val onCheckedTask: (Task,Boolean) -> Unit
) : Adapter<TaskAdapter.TaskViewHolder>() {

    private var list = mutableListOf<Task>()

    fun setList(newList: MutableList<Task>){
        this.list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binds(list[position])
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun binds(task: Task) = with(binding) {
            tvTitle.text = task.title
            tvDesc.text = task.description
            checkBox.isChecked = task.checkBox

            itemView.setOnLongClickListener {
                onLongClickTask(task) // deleteTaskClick(task)
                true
            }

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                onCheckedTask(task,isChecked)
            }
        }
    }
}