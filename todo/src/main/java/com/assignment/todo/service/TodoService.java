package com.assignment.todo.service;

import com.assignment.todo.dto.TodoRequestDto;
import com.assignment.todo.dto.TodoResponseDto;

public interface TodoService {
    TodoResponseDto saveTodo(TodoRequestDto requestDto);
}
