package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Mark;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarkMapper {
    public static Mark getMarkFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        Mark mark = new Mark();
        mark.setId(resultSet.getInt("id"));
        mark.setUserId(resultSet.getInt("user_id"));
        mark.setLessonId(resultSet.getInt("lesson_id"));
        mark.setValue(resultSet.getInt("value"));
        return mark;
    }
}
