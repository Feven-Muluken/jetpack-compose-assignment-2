package com.example.offlinetodos.data.remote

import com.example.offlinetodos.domain.model.Todo
import retrofit2.http.GET

interface TodoApiService {
    @GET("/todos")
    suspend fun getTodos(): List<Todo>
}
