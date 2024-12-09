package com.assignment.todo.repository;

import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.entity.Todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository {
    TodoResponseDto saveTodo(Todo todo);
    List<TodoResponseDto> findAllTodos(String authhor, LocalDate updatedDate);
}
