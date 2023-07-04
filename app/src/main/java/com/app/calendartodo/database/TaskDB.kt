package com.app.calendartodo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDB : RoomDatabase() {
    abstract fun taskDao() : TaskDao
    @InternalCoroutinesApi
    companion object {
        private var INSTANCE : TaskDB? = null
        fun getTaskDB(contex: Context) : TaskDB? {
            if (INSTANCE == null) {
                kotlinx.coroutines.internal.synchronized(TaskDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        contex.applicationContext, TaskDB::class.java, "taskDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}