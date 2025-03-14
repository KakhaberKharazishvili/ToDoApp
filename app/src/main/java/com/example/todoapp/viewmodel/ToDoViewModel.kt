package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.ToDo
import com.example.todoapp.data.local.ToDoDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = ToDoDatabase.getDatabase(application).toDoDao()

    private val _todos = MutableStateFlow<List<ToDo>>(emptyList())
    val todos: StateFlow<List<ToDo>> = _todos

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            todoDao.getAllTodosSortedByDeadline().collect { todoList ->
                _todos.value = todoList
            }
        }
    }

    fun updateDeadline(todo: ToDo, newDeadline: Long?) {
        viewModelScope.launch {
            val updatedTodo = todo.copy(deadline = newDeadline)
            todoDao.insert(updatedTodo)
        }
    }

    fun addTodo(title: String) {
        viewModelScope.launch {
            val newTodo = ToDo(title = title)
            todoDao.insert(newTodo)
        }
    }

    fun deleteTodo(todo: ToDo) {
        viewModelScope.launch {
            todoDao.delete(todo)
        }
    }

    fun updateTodo(todo: ToDo, newTitle: String) {
        viewModelScope.launch {
            val updatedTodo = todo.copy(title = newTitle)
            todoDao.insert(updatedTodo)
        }
    }
}
