package com.example.todoapp.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.viewmodel.ToDoViewModel
import com.example.todoapp.data.local.ToDo
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogComponent(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberDatePickerState()
    val context = LocalContext.current

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedDateMillis = state.selectedDateMillis
                if (selectedDateMillis != null) {
                    onDateSelected(selectedDateMillis)
                }
                onDismiss()
            }) {
                Text("ОК")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    ) {
        DatePicker(state = state)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(viewModel: ToDoViewModel = viewModel()) {
    val todos by viewModel.todos.collectAsState()
    var filterType by remember { mutableStateOf("Все") }
    val scope = rememberCoroutineScope()
    var newTask by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (newTask.isNotBlank()) {
                    viewModel.addTodo(newTask)
                    newTask = ""
                }
            }, modifier = Modifier.testTag("addTaskButton")) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = newTask,
                onValueChange = { newTask = it },
                placeholder = { Text("Новая задача") },
                modifier = Modifier.fillMaxWidth().testTag("taskInput"),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Все", "С дедлайном", "Без дедлайна").forEach { filter ->
                    OutlinedButton(onClick = { filterType = filter }, modifier = Modifier.weight(1f)) {
                        Text(filter, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val filteredTodos = when (filterType) {
                "С дедлайном" -> todos.filter { it.deadline != null }
                "Без дедлайна" -> todos.filter { it.deadline == null }
                else -> todos
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredTodos, key = { it.id }) { todo ->
                    val dismissState = rememberDismissState(
                        confirmValueChange = { dismissValue ->
                            if (dismissValue == DismissValue.DismissedToStart) {
                                scope.launch {
                                    viewModel.deleteTodo(todo)
                                }
                                true
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Gray)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Удалить", tint = Color.White)
                            }
                        },
                        dismissContent = {
                            ToDoItem(
                                todo = todo,
                                onDelete = { viewModel.deleteTodo(todo) },
                                onEdit = { newTitle -> viewModel.updateTodo(todo, newTitle) },
                                onSetDeadline = { newDeadline -> viewModel.updateDeadline(todo, newDeadline) }
                            )
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ToDoItem(todo: ToDo, onDelete: () -> Unit, onEdit: (String) -> Unit, onSetDeadline: (Long?) -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    var isPickingDate by remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    if (isPickingDate) {
        DatePickerDialogComponent(
            onDateSelected = { selectedDate ->
                onSetDeadline(selectedDate)
                isPickingDate = false
            },
            onDismiss = { isPickingDate = false }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = todo.title, style = MaterialTheme.typography.bodyLarge)
                Row {
                    IconButton(onClick = { isEditing = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Удалить", tint = Color.Gray)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { isPickingDate = true }) {
                Text(text = todo.deadline?.let { "Дедлайн: ${dateFormatter.format(it)}" } ?: "Установить дедлайн")
            }
        }
    }

    if (isEditing) {
        var editedText by remember { mutableStateOf(todo.title) }

        AlertDialog(
            onDismissRequest = { isEditing = false },
            title = { Text("Редактировать задачу") },
            text = {
                TextField(
                    value = editedText,
                    onValueChange = { editedText = it },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (editedText.isNotBlank()) {
                        onEdit(editedText)
                        isEditing = false
                    }
                }) { Text("Сохранить") }
            },
            dismissButton = {
                Button(onClick = { isEditing = false }) { Text("Отмена") }
            }
        )
    }
}


