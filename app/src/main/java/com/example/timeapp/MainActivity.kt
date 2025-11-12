package com.example.timeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.timeapp.db.SQLiteManager
import com.example.timeapp.viewadapter.TaskViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var db: SQLiteManager
    private lateinit var taskViewAdapter: TaskViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val addTaskButton: FloatingActionButton = findViewById(R.id.fabAddTask)
        addTaskButton.setOnClickListener { _ ->
            val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(this@MainActivity)
            val view: View = LayoutInflater.from(this@MainActivity).inflate(R.layout.bottom_sheet_add_task, null)

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()

            val textInputLayout: TextInputLayout = view.findViewById(R.id.tilTaskFieldLayout)
            val editText: TextInputEditText = view.findViewById(R.id.tiAddTaskEditText)
            val saveButton: Button = view.findViewById(R.id.btnSaveTask)

            saveButton.setOnClickListener {
                val taskTitle = editText.text.toString()
                if (taskTitle.isEmpty() || taskTitle.isBlank()) {
                    textInputLayout.error = "Type a correct task"
                } else {
                    db.addTask(taskTitle.trim())
                    taskViewAdapter.refreshData(db.getAllTasks())
                    Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.dismiss()
                }
            }

        }

        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.srlRefreshTasks)
        refreshLayout.setOnRefreshListener {
            refreshTaskList()
            refreshLayout.isRefreshing = false
        }

        db = SQLiteManager(this@MainActivity)
        taskViewAdapter = TaskViewAdapter(db.getAllTasks()) { task ->
            db.updateIsChecked(task)
        }

        recyclerView = findViewById(R.id.rvTasks)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = taskViewAdapter
        recyclerView.setHasFixedSize(true)

    }

    private fun refreshTaskList() {
        taskViewAdapter.refreshData(db.getAllTasks())
    }

//    override fun onResume() {
//        super.onResume()
//    }

}