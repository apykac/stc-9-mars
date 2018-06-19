package ru.innopolis.stc9.dao.mappers;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.DBObject;

public interface Mapper {
    DBObject getByParam(MultiValueMap<String, String> incParam);
}
