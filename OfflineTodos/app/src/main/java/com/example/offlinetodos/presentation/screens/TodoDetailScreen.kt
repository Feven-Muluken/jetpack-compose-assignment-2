package com.example.offlinetodos.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.offlinetodos.AppTopBar
import com.example.offlinetodos.presentation.viewmodel.TodoViewModel

@Composable
fun TodoDetailScreen(todoId: Int, viewModel: TodoViewModel, onBackClick: () -> Unit = {}) {
    val todo = viewModel.todos.collectAsState().value.find { it.id == todoId }
    Scaffold(
        topBar = {
            AppTopBar(title = "Todo Detail", onBackClick = onBackClick)
        }
    ) { innerPadding ->
        todo?.let {
            Column(modifier = Modifier.padding(innerPadding)
                .padding(16.dp)         // Additional content padding
                .fillMaxSize().padding(16.dp)) {
                Text("Title: ${it.title}")
                Text("Completed: ${it.completed}")
                Text("User ID: ${it.userId}")
                Text("ID: ${it.id}")
            }
        } ?: Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Todo not found")
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppTopBar(
//    title: String,
//    onBackClick: (() -> Unit)? = null
//) {
//    TopAppBar(
//        title = { Text(title) },
//        navigationIcon = {
//            if (onBackClick != null) {
//                IconButton(onClick = onBackClick) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = "Back"
//                    )
//                }
//            }
//        }
//    )
//}
