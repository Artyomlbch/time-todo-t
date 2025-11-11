package com.example.timeapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction
import com.example.timeapp.db.ToDoContract.ToDoColumns.COLUMN_ID
import com.example.timeapp.db.ToDoContract.ToDoColumns.COLUMN_IS_CHECKED
import com.example.timeapp.db.ToDoContract.ToDoColumns.COLUMN_TITLE
import com.example.timeapp.db.ToDoContract.ToDoEntry.DATABASE_NAME
import com.example.timeapp.db.ToDoContract.ToDoEntry.DATABASE_VERSION
import com.example.timeapp.db.ToDoContract.ToDoEntry.SQL_CREATE_ENTRIES
import com.example.timeapp.db.ToDoContract.ToDoEntry.SQL_DELETE_ENTRIES
import com.example.timeapp.db.ToDoContract.ToDoEntry.SQL_SELECT_ALL
import com.example.timeapp.db.ToDoContract.ToDoEntry.TABLE_NAME
import com.example.timeapp.model.TaskModel

class SQLiteManager(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun addTask(taskTitle: String) {
        val values = ContentValues()
        values.put("title", taskTitle)
        values.put("isChecked", 0)

        val db = writableDatabase
        db.transaction {
            db.insert(TABLE_NAME, null, values)
        }

    }

    fun getAllTasks(): List<TaskModel> {
        val tasks: ArrayList<TaskModel> = ArrayList()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery(SQL_SELECT_ALL, null)

        val idColumnIndex = cursor.getColumnIndex(COLUMN_ID)
        val titleColumnIndex = cursor.getColumnIndex(COLUMN_TITLE)
        val isCheckedColumnIndex = cursor.getColumnIndex(COLUMN_IS_CHECKED)

        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(idColumnIndex)
            val title: String = cursor.getString(titleColumnIndex)
            val isChecked: Boolean = cursor.getInt(isCheckedColumnIndex) != 0

            tasks.add(TaskModel(id, title, isChecked))
        }
        cursor.close()

        return tasks
    }

    fun updateIsChecked(task: TaskModel) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_CHECKED, task.isChecked)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.id.toString())

        db.update(TABLE_NAME, values, whereClause, whereArgs)
    }
}