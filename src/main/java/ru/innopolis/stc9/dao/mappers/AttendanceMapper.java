package ru.innopolis.stc9.dao.mappers;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.DBObject;

public class AttendanceMapper implements Mapper {
    //public static final String ID = "id";
    //public static final String LESSONID = "lesson";
    //public static final String USERID = "user";
    public static final String ATTENDED = "attended";

    @Override
    public DBObject getByParam(MultiValueMap<String, String> incParam) {
        return null;
    }
}
