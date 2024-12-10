package com.assignment.todo.dto;

import lombok.Getter;

@Getter //Lombok 어노테이션으로, 클래스 필드에 대한 Getter 메서드를 자동생성
public class TodoRequestDto {

    private String author; //작성자 이름
    private String password; //비밀번호
    private String title; //할 일 제목
}
