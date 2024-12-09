package com.assignment.todo.repository;

import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.entity.Todo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    TodoResponseDto saveTodo(Todo todo);
    List<TodoResponseDto> findAllTodos(String authhor, LocalDate updatedDate);
    Optional<TodoResponseDto> findTodoById(Long id);
    int updatedTodo(Long id, String author, String password, String title);
}
