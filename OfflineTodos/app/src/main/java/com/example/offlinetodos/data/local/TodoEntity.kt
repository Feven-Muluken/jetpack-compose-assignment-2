package com.example.offlinetodos.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.offlinetodos.domain.model.Todo


@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)

fun TodoEntity.toDomain() = Todo(userId, id, title, completed)
fun Todo.toEntity() = TodoEntity(id, userId, title, completed)
