package com.example.offlinetodos.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.offlinetodos.presentation.viewmodel.TodoViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.offlinetodos.presentation.screens.TodoDetailScreen
import com.example.offlinetodos.presentation.screens.TodoListScreen

@Composable
fun NavGraph(navController: NavHostController, viewModel: TodoViewModel, modifier: Modifier) {
    NavHost(navController, startDestination = "list", modifier = modifier) {
        composable("list") {
            TodoListScreen(viewModel = viewModel, onTodoClick = { todoId ->
                navController.navigate("detail/$todoId")
            }
            ) {
                navController.navigate("detail/$it")
            }
        }
        composable("detail/{todoId}") { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")?.toIntOrNull() ?: return@composable
            TodoDetailScreen(todoId, viewModel)
        }
        composable("detail/{todoId}") { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")?.toIntOrNull() ?: return@composable
            TodoDetailScreen(
                todoId = todoId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("list") {
            TodoListScreen(
                viewModel = viewModel,
                onTodoClick = { todoId -> navController.navigate("detail/$todoId") },
                onDeleteClick = { todoId -> viewModel.deleteTodoById(todoId) }
            )
        }
    }
}
