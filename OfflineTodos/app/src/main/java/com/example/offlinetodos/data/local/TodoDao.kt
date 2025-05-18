package com.example.offlinetodos.data.local

import androidx.room.*
import com.example.offlinetodos.domain.model.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    suspend fun getAllTodos(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<TodoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)
//
    @Update
    suspend fun updateTodo(todo: TodoEntity)
//
//    @Delete
//    suspend fun deleteTodo(todo: TodoEntity)
//
//    @Query("DELETE FROM todos")
//    suspend fun clearAll()
      @Query("DELETE FROM todos WHERE id = :id")
      suspend fun deleteById(id: Int)


}
