package com.assignment.todo.dto;

import com.assignment.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    private Long id;
    private String author;
    private String password;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.author = todo.getAuthor();
        this.password = todo.getPassword();
        this.title = todo.getTitle();
        this.createdDate = todo.getCreatedDate();
        this.updatedDate = todo.getUpdatedDate();
    }
}
