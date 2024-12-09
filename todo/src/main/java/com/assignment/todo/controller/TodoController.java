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
            @RequestParam(required = false) LocalDate updatedDate) {
        return todoService.findAllTodos(author, updatedDate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updatedTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        return new ResponseEntity<>(todoService.updatedTodo(id, requestDto.getAuthor(), requestDto.getPassword(), requestDto.getTitle()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponseDto> deleteTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        todoService.deleteTodo(id, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
