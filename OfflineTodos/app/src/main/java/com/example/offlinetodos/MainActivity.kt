package com.example.offlinetodos

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
////import androidx.compose.material3
////import androidx.compose.material3
import androidx.compose.material3.*
//import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.offlinetodos.data.local.AppDatabase
import com.example.offlinetodos.data.remote.TodoApiService
import com.example.offlinetodos.data.repository.TodoRepository
import com.example.offlinetodos.presentation.navigation.NavGraph
import com.example.offlinetodos.presentation.viewmodel.TodoViewModel
import com.example.offlinetodos.presentation.viewmodel.TodoViewModelFactory
import com.example.offlinetodos.ui.theme.OfflineTodosTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "todo-db"
        ).build()

        val api = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApiService::class.java)
        val todoDao = db.todoDao()
        val repository = TodoRepository(api, todoDao)
        val viewModelFactory = TodoViewModelFactory(repository)

        setContent {
            OfflineTodosTheme {
                var showStartScreen by remember { mutableStateOf(true) }

                val navController = rememberNavController()
                val viewModel: TodoViewModel = viewModel(factory = viewModelFactory)
                Scaffold(modifier = Modifier.fillMaxSize()

                ) { innerPadding ->
                    if (showStartScreen) {
                        // Start Screen UI
                        WelcomeScreen(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            onStartClicked = { showStartScreen = false }
                        )
                    }else {
                    NavGraph(
                        navController = navController,
                        viewModel = viewModel(factory = viewModelFactory),
                        modifier = Modifier.padding(innerPadding)
                    )}
                }
            }
        }
    }
}


@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onStartClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Offline TODO App",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(onClick = onStartClicked) {
            Text("Start")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}
