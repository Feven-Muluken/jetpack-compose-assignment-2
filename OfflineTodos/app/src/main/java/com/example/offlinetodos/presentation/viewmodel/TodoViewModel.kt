package com.example.offlinetodos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlinetodos.data.repository.TodoRepository
import com.example.offlinetodos.domain.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            repository.getTodos().collect {
                _todos.value = it
            }
        }
    }
    fun deleteTodoById(id: Int) {
        viewModelScope.launch {
            repository.deleteTodoById(id)
            loadTodos() // reload after deletion
        }
    }
    fun addTodo(title: String) {
        val newTodo = Todo(
            userId = 1,
            id = (todos.value.maxOfOrNull { it.id } ?: 0) + 1,
            title = title,
            completed = false
        )
        viewModelScope.launch {
            repository.addTodo(newTodo)
            repository.insertTodo(newTodo)
//            val updatedTodos = repository.getTodos()
//            _todos.value = updatedTodos
            repository.getTodos().collect { list ->
                _todos.value = list
            }
//            loadTodos() // Refresh UI
        }
    }
    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

}
