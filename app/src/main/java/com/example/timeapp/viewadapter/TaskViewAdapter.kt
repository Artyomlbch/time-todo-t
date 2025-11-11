package com.example.timeapp.viewadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timeapp.R
import com.example.timeapp.db.SQLiteManager
import com.example.timeapp.model.TaskModel

class TaskViewAdapter(
    private var tasks: List<TaskModel>,
    private val onStatusChanged: (TaskModel) -> Unit): RecyclerView.Adapter<TaskViewAdapter.TaskViewHolder>() {


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
        holder.taskCheckBox.setOnCheckedChangeListener(null)

        val currentTask: TaskModel = tasks[position]
        holder.task.text = currentTask.title
        holder.taskCheckBox.isChecked = currentTask.isChecked

        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            currentTask.isChecked = isChecked
            onStatusChanged(currentTask)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val task: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.cbTaskCheckBox)
    }

    fun refreshData(newTasks: List<TaskModel>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}