package ru.innopolis.stc9.dao.mappers;

import ru.innopolis.stc9.pojo.Mark;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarkMapper {
    private MarkMapper() {
    }

    public static Mark getMarkFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        Mark mark = new Mark();
        mark.setId(resultSet.getInt("id"));
        mark.setUserId(resultSet.getInt("user_id"));
        mark.setLessonId(resultSet.getInt("lesson_id"));
        mark.setValue(resultSet.getInt("value"));
        mark.setComment(resultSet.getString("comment"));
        return mark;
    }
}
