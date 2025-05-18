package com.example.offlinetodos.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.offlinetodos.presentation.viewmodel.TodoViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Alignment
import com.example.offlinetodos.AppTopBar
import com.example.offlinetodos.domain.model.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(viewModel: TodoViewModel, onTodoClick: (Int) -> Unit ,onDeleteClick: (Int) -> Unit ) {
    val todos by viewModel.todos.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<Todo?>(null) }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 20.dp),
                title = {Text("TODO List")},
                actions = {
                    FloatingActionButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add TODO")
                    }
                }
            )
        }
    ){  padding ->
        
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(todos) { todo ->
                    TodoCard(
                        todo = todo,
                         onTodoClick = { onTodoClick(todo.id) },
                        onEditClick = { showEditDialog = todo },
                        onDeleteClick = { viewModel.deleteTodoById(todo.id) }
                    )
                }
            }
        }
        // Add TODO Dialog
        if (showAddDialog) {
            AddTodoDialog(
                onDismiss = { showAddDialog = false },
                onAddTodo = { title ->
                    viewModel.addTodo(title)
                    showAddDialog = false
                }
            )
        }
        showEditDialog?.let { todo ->
            EditTodoDialog(
                todo = todo,
                onDismiss = { showEditDialog = null },
                onConfirm = { updated ->
                    viewModel.updateTodo(updated)
                    showEditDialog = null
                }
            )
        }
    }
}


@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onAddTodo: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (title.isNotBlank()) {
                    onAddTodo(title)
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("New TODO") },
        text = {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true
            )
        }
    )
}

@Composable
fun EditTodoDialog(
    todo: Todo,
    onDismiss: () -> Unit,
    onConfirm: (Todo) -> Unit
) {
    var title by remember { mutableStateOf(todo.title) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit TODO") },
        text = {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") }
            )
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(todo.copy(title = title))
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Composable
fun TodoCard(
    todo: Todo,
    onTodoClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onTodoClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(todo.title, style = MaterialTheme.typography.bodyLarge)
                Text(if (todo.completed) "Completed" else "Pending", style = MaterialTheme.typography.bodySmall)
            }

            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}