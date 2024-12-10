package com.assignment.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter //Lombok 어노테이션으로 클래스 필드에 대한 Getter 메서드를 자동생성
@AllArgsConstructor //모든 필드를 포함한 생성자를 자동생성
public class Todo {

    @Setter //Lombok 어노테이션으로 ID 필드에 Setter 메서드를 자동생성
    private Long id; //고유 ID
    private String author; //작성자 이름
    private String password; //비밀번호
    private String title; //할 일 제목
    private LocalDateTime createdDate; //할 일 생성 날짜 및 시간
    private LocalDateTime updatedDate; //할 일 마지막 업데이트 날짜 및 시간

    /**
     * ID와 날짜 정보 없이 할 일을 생성할 때 사용하는 생성자
     * @param author 작성자 이름
     * @param password 비밀번호
     * @param title 할 일 제목
     */
    public Todo(String author, String password, String title) {
        this.author = author;
        this.password = password;
        this.title = title;
    }
}
