package ru.volkov.integration.batchess.input.fromdb;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();
        student.setName(resultSet.getString("studentName"));
        student.setGroup(resultSet.getString("groupName"));
        return student;
    }
}
