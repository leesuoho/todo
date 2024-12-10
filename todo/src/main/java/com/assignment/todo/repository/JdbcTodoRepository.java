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

@Repository //데이터 접근 계층
public class JdbcTodoRepository implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 생성자를 통해 JdbcTemplate을 초기화
     * @param dataSource 데이터 소스
     */
    public JdbcTodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 새로운 할 일을 저장
     * @param todo 저장할 할 일 엔티티
     * @return 저장된 할 일의 정보
     */
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

    /**
     * 모든 할 일을 조회. 작성자나 업데이트 날짜로 필터링할 수 있다.
     * @param author 작성자 이름
     * @param updatedDate 업데이트 날짜
     * @return 필터링된 할 일 목록
     */
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
//      jdbcTemplate.query(String sql쿼리, Object[] sql쿼리 ?에 들어갈 값들, RowMapper<T> ResultSet을 매핑해 원하는 객체로 변환);
        return jdbcTemplate.query(queryStringBuilder.toString(), params.toArray(), todoRowMapper());
    }

    /**
     * 특정 ID의 할 일을 조회
     * @param id 조회하려는 할 일의 ID
     * @return 해당 할 일의 정보
     */
    @Override
    public Optional<TodoResponseDto> findTodoById(Long id) {
        List<TodoResponseDto> result = jdbcTemplate.query("select * from todo where id = ?", todoRowMapper(), id);
        return result.stream().findAny();
    }

    /**
     * ResultSet을 TodoResponseDto 객체로 매핑
     * @return RowMapper<TodoResponseDto> 객체
     */
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

    /**
     * 할 일을 업데이트
     * @param id 업데이트하려는 할 일의 ID
     * @param author 새로운 작성자 이름
     * @param password 비밀번호
     * @param title 새로운 할 일 제목
     * @return 업데이트된 행 수
     */
    @Override
    public int updatedTodo(Long id, String author, String password, String title) {
        StringBuilder queryStringBuilder = new StringBuilder("UPDATE todo SET ");
        List<Object> params = new ArrayList<>();

        if (author != null && title != null) {
            queryStringBuilder.append("author = ?, title = ? ");
            params.add(author);
            params.add(title);
        } else if (author != null && title == null) {
            queryStringBuilder.append("author = ? ");
            params.add(author);
        } else if (author == null && title != null) {
            queryStringBuilder.append("title = ? ");
            params.add(title);
        }

        queryStringBuilder.append("WHERE id = ? AND password = ?");
        params.add(id);
        params.add(password);

        return jdbcTemplate.update(queryStringBuilder.toString(), params.toArray());
    }

    /**
     * 특정 ID의 할 일을 삭제
     * @param id 삭제하려는 할 일의 ID
     * @param password 비밀번호
     * @return 삭제된 행 수
     */
    @Override
    public int deleteTodo(Long id, String password) {
        return jdbcTemplate.update("DELETE FROM todo WHERE id = ? AND password = ?", id, password);
    }
}
