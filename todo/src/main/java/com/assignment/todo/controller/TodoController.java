package com.assignment.todo.controller;

import com.assignment.todo.dto.TodoRequestDto;
import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController //Restful API 요청을 처리하는 컨트롤러
@RequestMapping("/todos") //URL 매핑을 설정
public class TodoController {

    private final TodoService todoService;

    /**
     * 생성자
     * @param todoService
     */
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * 할 일을 생성
     * @param requestDto
     * @return 생성된 할 일의 정보와 Http 상태
     */
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {
        return new ResponseEntity<>(todoService.saveTodo(requestDto), HttpStatus.OK);
    }

    /**
     * 모든 할 일을 조회
     * @param author 작성자 이름
     * @param updatedDate 마지막 업데이트 날짜
     * @return 할 일 목록과 Http 상태
     */
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findAllTodos(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) LocalDate updatedDate) {
        return todoService.findAllTodos(author, updatedDate);
    }

    /**
     * 특정 ID의 할 일을 조회
     * @param id
     * @return 해당 할 일의 정보와 Http 상태
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }

    /**
     * 특정 ID의 할 일을 업데이트
     * @param id
     * @param requestDto 업데이트할 데이터
     * @return 업데이트된 할 일의 정보와 Http 상태
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updatedTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        return new ResponseEntity<>(todoService.updatedTodo(id, requestDto.getAuthor(), requestDto.getPassword(), requestDto.getTitle()), HttpStatus.OK);
    }

    /**
     * 특정 ID의 할 일을 삭제
     * @param id
     * @param requestDto 삭제 요청 데이터
     * @return Http 상태 코드
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponseDto> deleteTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        todoService.deleteTodo(id, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
