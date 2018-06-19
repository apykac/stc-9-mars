package ru.innopolis.stc9.dao.mappers;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.DBObject;

public class LessonMapper implements Mapper {
    public static final String ID = "id";
    public static final String SUBJID = "subjectId";
    public static final String SNAME = "sname";
    public static final String DATE = "date";
    public static final String NAME = "name";

    @Override
    public DBObject getByParam(MultiValueMap<String, String> incParam) {
        return null;
    }
}
