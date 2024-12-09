package com.assignment.todo.repository;

import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.entity.Todo;

public interface TodoRepository {
    TodoResponseDto saveTodo(Todo todo);
}
