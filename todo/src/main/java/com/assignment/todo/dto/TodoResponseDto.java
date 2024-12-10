package com.assignment.todo.dto;

import com.assignment.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter //Lombok 어노테이션으로, 클래스 필드에 대한 Getter 메서드를 자동생성
@AllArgsConstructor //모든 필드를 포함한 생성자를 자동생성
public class TodoResponseDto {

    private Long id; //고유 ID
    private String author; //작성자 이름
    private String password; //비밀번호
    private String title; //할 일 제목
    private LocalDateTime createdDate; //할 일 생성 날짜 및 시간
    private LocalDateTime updatedDate; //할 일 마지막 업데이트 날짜 및 시간

    /**
     * Todo 엔티티를 기반으로 TodoResponseDto 객체생성
     * @param todo Todo 엔티티 객체
     */
    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.author = todo.getAuthor();
        this.password = todo.getPassword();
        this.title = todo.getTitle();
        this.createdDate = todo.getCreatedDate();
        this.updatedDate = todo.getUpdatedDate();
    }
}
