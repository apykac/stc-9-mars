package ru.innopolis.stc9.dao.mappers;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.DBObject;

public class HomeWokMapper implements Mapper {
    public static final String ID = "id";
    public static final String HWURL = "homeWorkURL";

    @Override
    public DBObject getByParam(MultiValueMap<String, String> incParam) {
        return null;
    }
}
