package com.assignment.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Todo {

    @Setter
    private Long id;
    private String author;
    private String password;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Todo(String author, String password, String title) {
        this.author = author;
        this.password = password;
        this.title = title;
    }
}
