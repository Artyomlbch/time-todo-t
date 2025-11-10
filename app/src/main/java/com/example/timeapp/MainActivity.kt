package com.example.timeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timeapp.model.TaskModel
import com.example.timeapp.viewadapter.TaskViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tasks: ArrayList<TaskModel>
    private lateinit var addTaskButton: FloatingActionButton
    var titles: ArrayList<String> = ArrayList()
    var boxChecks: ArrayList<Boolean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addTaskButton = findViewById(R.id.fabAddTask)
        addTaskButton.setOnClickListener {v ->
            val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(this@MainActivity)
            val view: View = LayoutInflater.from(this@MainActivity).inflate(R.layout.bottom_sheet_add_task, null)

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()

            val textInputLayout: TextInputLayout = view.findViewById(R.id.tilTaskFieldLayout)
            val editText: TextInputEditText = view.findViewById(R.id.tiAddTaskEditText)

            bottomSheetDialog.setOnDismissListener {
                Toast.makeText(this@MainActivity, "Bottom sheet dismissed", Toast.LENGTH_SHORT).show()
            }
        }

        fillArrays()
        recyclerView = findViewById(R.id.rvTasks)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.setHasFixedSize(true)

        tasks = arrayListOf<TaskModel>()
        getData()

    }

    private fun fillArrays() {
        for (i in 1..15) {
            titles.add("Task $i")
            boxChecks.add(false)
        }
    }

    private fun getData() {
        for (i in titles.indices) {
            val task: TaskModel = TaskModel(titles[i], boxChecks[i])
            tasks.add(task)
        }

        recyclerView.adapter = TaskViewAdapter(tasks)
    }
}