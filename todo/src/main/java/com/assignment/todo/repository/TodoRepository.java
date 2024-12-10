package com.assignment.todo.repository;

import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.entity.Todo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Todo 데이터 접근 계층의 인터페이스
 * 데이터베이스 작업을 수행하는 메서드
 */
public interface TodoRepository {
    /**
     * 새로운 할 일을 저장
     * @param todo 저장할 할 일 엔티티
     * @return 저장된 할 일의 정보
     */
    TodoResponseDto saveTodo(Todo todo);

    /**
     * 모든 할 일을 조회. 작성자나 업데이트 날짜로 필터링할 수 있다.
     * @param authhor 작성자 이름
     * @param updatedDate 업데이트 날짜
     * @return 필터링된 할 일 목록
     */
    List<TodoResponseDto> findAllTodos(String authhor, LocalDate updatedDate);

    /**
     * 특정 ID의 할 일을 조회
     * @param id 조회하려는 할 일의 ID
     * @return 해당 할 일의 정보 (존재하지 않으면 Optional 반환)
     */
    Optional<TodoResponseDto> findTodoById(Long id);

    /**
     * 특정 ID의 할 일을 업데이트
     * @param id 업데이트하려는 할 일의 ID
     * @param author 새로운 작성자 이름
     * @param password 비밀번호
     * @param title 새로운 할 일 제목
     * @return 업데이트된 행 수 (0이면 업데이트 실패)
     */
    int updatedTodo(Long id, String author, String password, String title);

    /**
     * 특정 ID의 할 일을 삭제
     * @param id 삭제하려는 할 일의 ID
     * @param password 비밀번호
     * @return 삭제된 행 수 (0이면 삭제 실패)
     */
    int deleteTodo(Long id, String password);
}
