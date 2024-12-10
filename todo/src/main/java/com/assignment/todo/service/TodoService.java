package com.assignment.todo.service;

import com.assignment.todo.dto.TodoRequestDto;
import com.assignment.todo.dto.TodoResponseDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * Todo 관리 서비스의 인터페이스
 * 할 일 생성, 조회, 수정, 삭제와 관련된 메서드
 */
public interface TodoService {
    /**
     * 새로운 할 일을 저장
     * @param requestDto 클라이언트에서 보낸 요청 데이터
     * @return 생성된 할 일의 정보
     */
    TodoResponseDto saveTodo(TodoRequestDto requestDto);

    /**
     * 모든 할 일을 조회. 작성자나 업데이트 날짜로 필터링할 수 있다.
     * @param author 작성자 이름
     * @param updatedDate 업데이트 날짜
     * @return 필터링된 할 일 목록
     */
    ResponseEntity<List<TodoResponseDto>> findAllTodos(String author, LocalDate updatedDate);

    /**
     * 특정 ID의 할 일을 조회
     * @param id 조회하려는 할 일의 id
     * @return 해당 할 일의 정보
     */
    TodoResponseDto findTodoById(Long id);

    /**
     * 특정 ID의 할 일을 업데이트
     * @param id 업데이트하려는 할 일의 ID
     * @param author 작성자 이름
     * @param password 비밀번호
     * @param title 새로운 할 일 제목
     * @return 업데이트된 할 일의 정보
     */
    TodoResponseDto updatedTodo(Long id, String author, String password, String title);

    /**
     * 특정 ID의 할 일을 삭제
     * @param id 삭제하려는 할 일의 ID
     * @param password 비밀번호
     */
    void deleteTodo(Long id, String password);
}
