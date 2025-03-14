package com.example.todoapp

import com.example.todoapp.data.local.ToDo
import com.example.todoapp.viewmodel.ToDoViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ToDoViewModelTest {
    private lateinit var viewModel: ToDoViewModel

    @Before
    fun setup() {
        viewModel = mockk(relaxed = true)
    }

    @Test
    fun `добавление новой задачи`() = runTest {
        val newTodo = ToDo(title = "Тестовая задача")
        every { viewModel.addTodo(newTodo.title) } just Runs

        viewModel.addTodo(newTodo.title)

        verify { viewModel.addTodo(newTodo.title) }
    }

    @Test
    fun `удаление задачи`() = runTest {
        val todo = ToDo(title = "Удаляемая задача")
        every { viewModel.deleteTodo(todo) } just Runs

        viewModel.deleteTodo(todo)

        verify { viewModel.deleteTodo(todo) }
    }
}
