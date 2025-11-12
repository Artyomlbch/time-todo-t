package com.example.timeapp.model

import java.util.Date

data class TaskModel(
    var id: Int,
    var title: String,
    var isChecked: Boolean,
    var createdAt: Date)
