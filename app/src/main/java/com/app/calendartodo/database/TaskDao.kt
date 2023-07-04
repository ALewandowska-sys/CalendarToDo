package com.app.calendartodo.database

import androidx.room.*
import java.time.LocalDateTime

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table WHERE data_column LIKE :data order by priority_column ASC")
    fun getAllByDate(data: LocalDateTime) : List<Task>
    @Query("SELECT COUNT(*) FROM task_table WHERE done_column LIKE 'false'")
    fun getSizeToDo(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Task)
    @Query("DELETE FROM task_table")
    fun clearDB()
    @Delete
    fun delete(person: Task)
}