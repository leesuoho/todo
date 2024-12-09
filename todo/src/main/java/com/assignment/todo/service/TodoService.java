package com.assignment.todo.service;

import com.assignment.todo.dto.TodoRequestDto;
import com.assignment.todo.dto.TodoResponseDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface TodoService {
    TodoResponseDto saveTodo(TodoRequestDto requestDto);

    ResponseEntity<List<TodoResponseDto>> findAllTodos(String author, LocalDate updatedDate);

    TodoResponseDto findTodoById(Long id);

    TodoResponseDto updatedTodo(Long id, String author, String password, String title);

    void deleteTodo(Long id, String password);
}
