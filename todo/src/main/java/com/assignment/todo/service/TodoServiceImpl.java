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

@Service // 서비스 계층
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    /**
     * 생성자를 통해 TodoRepository를 주입받음
     * @param todoRepository 할 일 데이터 접근 계층
     */
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * 새로운 할 일을 저장
     * @param requestDto 클라이언트에서 보낸 요청 데이터
     * @return 저장된 할 일의 정보
     */
    @Override
    public TodoResponseDto saveTodo(TodoRequestDto requestDto) {
        Todo todo = new Todo(requestDto.getAuthor(), requestDto.getPassword(), requestDto.getTitle());

        return todoRepository.saveTodo(todo);
    }

    /**
     * 모든 할 일을 조회. 작성자나 업데이트 날짜로 필터링할 수 있다.
     * @param author 작성자 이름
     * @param updatedDate 업데이트 날짜
     * @return 필터링된 할 일 목록
     */
    @Override
    public ResponseEntity<List<TodoResponseDto>> findAllTodos(String author, LocalDate updatedDate) {
        List<TodoResponseDto> allTodos = todoRepository.findAllTodos(author, updatedDate);
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    /**
     * 특정 ID의 할 일을 조회
     * @param id 조회하려는 할 일의 id
     * @return 해당 할 일의 정보
     */
    //    Optional<Todo> findTodoById(Long id);
    @Override
    public TodoResponseDto findTodoById(Long id) {
        Optional<TodoResponseDto> todoResponseDto = todoRepository.findTodoById(id);
        return todoResponseDto.get();
    }

    /**
     * 특정 ID의 할 일을 업데이트한다
     * @param id 업데이트하려는 할 일의 ID
     * @param author 작성자 이름
     * @param password 비밀번호
     * @param title 새로운 할 일 제목
     * @return 업데이트된 할 일의 정보
     */
    @Transactional //데이터 변경 작업에 트랜잭션을 적용
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

    /**
     * 특정 ID의 할 일을 삭제
     * @param id 삭제하려는 할 일의 ID
     * @param password 비밀번호
     */
    @Override
    public void deleteTodo(Long id, String password) {

        int deleteRow = todoRepository.deleteTodo(id, password);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
