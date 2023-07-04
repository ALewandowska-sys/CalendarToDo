package com.app.calendartodo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "description_column")
    var description: String,
    @ColumnInfo(name = "color_column")
    var color: String,
    @ColumnInfo(name = "priority_column")
    var priority: Int,
    @ColumnInfo(name = "done_column", defaultValue = "false")
    var done: Boolean,
    @ColumnInfo(name = "data_column")
    var data: LocalDateTime
) { }