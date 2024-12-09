package com.assignment.todo.repository;

import com.assignment.todo.dto.TodoResponseDto;
import com.assignment.todo.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
//1. author와 updateDate가 존재
//2. author만 존재
//3. updateDate만 존재
//4. 둘 다 존재하지 않음(전체조회)
//    " WHERE name = ? AND DATE(editDate) = ?"
    @Override
    public List<TodoResponseDto> findAllTodos(String author, LocalDate updatedDate) {
        List<TodoResponseDto> todoResponseDtos = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder queryStringBuilder = new StringBuilder("SELECT * FROM todo");
        if (author != null && updatedDate != null) {
            queryStringBuilder.append(" WHERE author = ? AND DATE(updatedDate) = ?");
            params.add(author);
            params.add(updatedDate);
        } else if (author != null && updatedDate == null) {
            queryStringBuilder.append(" WHERE author = ?");
            params.add(author);
        } else if (author == null && updatedDate != null) {
            queryStringBuilder.append(" WHERE DATE(updatedDate) = ?");
            params.add(updatedDate);
        } else if (author == null && updatedDate == null) {
        }

        queryStringBuilder.append(" ORDER BY updatedDate DESC");
//        jdbcTemplate.query(String sql쿼리, Object[] sql쿼리 ?에 들어갈 값들, RowMapper<T> ResultSet을 매핑해 원하는 객체로 변환);
        return jdbcTemplate.query(queryStringBuilder.toString(), params.toArray(), todoRowMapper());
    }
    private RowMapper<TodoResponseDto> todoRowMapper() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("password"),
                        rs.getString("title"),
                        rs.getTimestamp("createdDate").toLocalDateTime(),
                        rs.getTimestamp("updatedDate").toLocalDateTime()
                );
            }
        };
    }
}
