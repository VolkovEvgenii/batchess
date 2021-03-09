package ru.volkov.integration.batchess.output.flatfile;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlatFileStudentGroupRowMapper implements RowMapper<FlatFileStudentGroup> {

    @Override
    public FlatFileStudentGroup mapRow(ResultSet resultSet, int i) throws SQLException {
        FlatFileStudentGroup studentGroup = new FlatFileStudentGroup();
        studentGroup.setStudentName(resultSet.getString("studentName"));
        studentGroup.setGroupName(resultSet.getString("groupName"));
        return studentGroup;
    }
}
