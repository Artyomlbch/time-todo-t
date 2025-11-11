package com.example.timeapp.db

import android.provider.BaseColumns

object ToDoContract {

    object ToDoEntry {
        const val DATABASE_NAME = "todo_db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "tasks"
        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "${ToDoColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${ToDoColumns.COLUMN_TITLE} TEXT," +
                    "${ToDoColumns.COLUMN_IS_CHECKED} INTEGER)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
        const val SQL_SELECT_ALL = "SELECT * FROM $TABLE_NAME"
    }

    object ToDoColumns {
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IS_CHECKED = "isChecked"
    }

}