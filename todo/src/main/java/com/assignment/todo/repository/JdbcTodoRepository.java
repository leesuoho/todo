package com.assignment.todo.repository;

import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTodoRepository implements TodoRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TodoResponseDto saveTodo(Todo todo) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("todo")
                .usingGeneratedKeyColumns("id")
                .usingColumns("author", "password", "title");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", todo.getAuthor());
        parameters.put("password", todo.getPassword());
        parameters.put("title", todo.getTitle());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new TodoResponseDto(key.longValue(), todo.getAuthor(), todo.getPassword(), todo.getTitle(), null, null);
    }
}
