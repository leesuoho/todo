package com.assignment.todo.service;

import com.assignment.todo.dto.TodoRequestDto;
import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.entity.Todo;
import com.assignment.todo.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

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

    @Transactional
    @Override
    public TodoResponseDto updatedTodo(Long id, String author, String password, String title) {
        if (author == null && title == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The author and title are required values.");
        }

        int updatedRow = todoRepository.updatedTodo(id, author, password, title);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }

        return todoRepository.findTodoById(id).get();
    }

    @Override
    public void deleteTodo(Long id, String password) {

        int deleteRow = todoRepository.deleteTodo(id, password);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
