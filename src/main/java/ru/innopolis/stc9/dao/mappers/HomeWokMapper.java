package ru.innopolis.stc9.dao.mappers;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.DBObject;

public class HomeWokMapper implements Mapper {
    public static final String ID = "id";
    public static final String HWURL = "homeWorkURL";
    //public static final String STID = "studentId";
    //public static final String LESSID = "lessonId";

    @Override
    public DBObject getByParam(MultiValueMap<String, String> incParam) {
        return null;
    }
}
