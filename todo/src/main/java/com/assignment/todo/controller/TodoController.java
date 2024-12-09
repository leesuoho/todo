package com.assignment.todo.controller;

import com.assignment.todo.dto.TodoRequestDto;
import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {
        return new ResponseEntity<>(todoService.saveTodo(requestDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findAllTodos(
            @RequestParam(required = false) String author,
            @RequestParam(required = false)LocalDate updatedDate) {
        return todoService.findAllTodos(author, updatedDate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }
}
