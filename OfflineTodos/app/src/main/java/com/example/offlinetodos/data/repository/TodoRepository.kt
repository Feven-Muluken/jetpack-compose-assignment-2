package com.example.offlinetodos.data.repository

import com.example.offlinetodos.data.local.TodoDao
import com.example.offlinetodos.data.local.toDomain
import com.example.offlinetodos.data.local.toEntity
import com.example.offlinetodos.data.remote.TodoApiService
import com.example.offlinetodos.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodoRepository(
    private val api: TodoApiService,
    private val todoDao: TodoDao
) {
    fun getTodos(): Flow<List<Todo>> = flow {
        val cached = todoDao.getAllTodos().map { it.toDomain() }
        emit(cached)

        try {
            val remote = api.getTodos()
            todoDao.insertAll(remote.map { it.toEntity() })
            emit(remote)
        } catch (e: Exception) {
            emit(cached)
        }
    }

    suspend fun fetchAndCacheTodos() {
        val remoteTodos = api.getTodos()
        todoDao.insertAll(remoteTodos.map { it.toEntity() })
    }
//
//    suspend fun getAllTodos(todo: Todo): List<Todo> {
//        return dao.getAllTodos().map { it.toDomain() }
//    }
//
    suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo.toEntity())
    }

    suspend fun insertTodo(todos: List<Todo>) {
        todoDao.insertAll(todos.map { it.toEntity() })
    }

    suspend fun addTodo(todo: Todo) {
        todoDao.insertTodo(todo.toEntity())
    }
    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo.toEntity())
    }
//
//    suspend fun deleteTodo(todo: Todo) {
//        dao.deleteTodo(todo.toEntity())
//    }
    suspend fun deleteTodoById(id: Int) {
    todoDao.deleteById(id)
}

}

