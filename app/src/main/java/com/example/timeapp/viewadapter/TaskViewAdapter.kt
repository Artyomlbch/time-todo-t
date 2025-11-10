package com.example.timeapp.viewadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timeapp.R
import com.example.timeapp.model.TaskModel

class TaskViewAdapter(private val tasks: ArrayList<TaskModel>): RecyclerView.Adapter<TaskViewAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_item, parent, false)

        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val currentItem: TaskModel = tasks[position]
        holder.task.text = currentItem.title
        holder.taskCheckBox.isChecked = currentItem.isChecked
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val task: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.cbTaskCheckBox)
    }
}