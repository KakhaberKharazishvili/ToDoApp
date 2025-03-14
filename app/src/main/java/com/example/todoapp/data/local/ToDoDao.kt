package com.example.todoapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_table ORDER BY dateCreated DESC")
    fun getAllTodosSortedByDate(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo_table ORDER BY deadline IS NULL, deadline ASC, dateCreated DESC")
    fun getAllTodosSortedByDeadline(): Flow<List<ToDo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: ToDo)

    @Delete
    suspend fun delete(todo: ToDo)

}
