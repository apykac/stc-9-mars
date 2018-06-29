package ru.innopolis.stc9.dao.mappers;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.DBObject;

public class MarkMapper implements Mapper {
    public static final String ID = "id";
    public static final String VALUE = "value";
    public static final String USERID = "userId";
    public static final String COMMENT = "comment";

    @Override
    public DBObject getByParam(MultiValueMap<String, String> incParam) {
        return null;
    }
}
