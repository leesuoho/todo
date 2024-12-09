package com.assignment.todo.service;

import com.assignment.todo.dto.TodoRequestDto;
import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.entity.Todo;
import com.assignment.todo.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoResponseDto saveTodo(TodoRequestDto requestDto) {
        Todo todo = new Todo(requestDto.getAuthor(), requestDto.getPassword(), requestDto.getTitle());

        return todoRepository.saveTodo(todo);
    }

    @Override
    public ResponseEntity<List<TodoResponseDto>> findAllTodos(String author, LocalDate updatedDate) {
        List<TodoResponseDto> allTodos = todoRepository.findAllTodos(author, updatedDate);
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }
//    Optional<Todo> findTodoById(Long id);
    @Override
    public TodoResponseDto findTodoById(Long id) {
        Optional<TodoResponseDto> todoResponseDto = todoRepository.findTodoById(id);
        return todoResponseDto.get();
    }
}
