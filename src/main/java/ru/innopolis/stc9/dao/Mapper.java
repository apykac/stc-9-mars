package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.DBObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Mapper {
    private static Logger logger = Logger.getLogger(Mapper.class);

    public abstract Map<String, Object[]> getSourceMap();

    public abstract DBObject getByResultSet(ResultSet resultSet) throws SQLException;

    public abstract DBObject getByParam(MultiValueMap<String, String> incParam);

    public String getSqlRequestByParam(String prefix, List<String> mainParam, String postfix, List<String> secondaryParam, boolean isEqually) {
        if (prefix == null) return null;
        StringBuilder result = new StringBuilder(prefix);
        if ((mainParam != null) && !mainParam.isEmpty()) result.append(listToString(mainParam, isEqually));
        if ((postfix != null) && !postfix.isEmpty()) result.append(postfix);
        if ((secondaryParam != null) && !secondaryParam.isEmpty())
            result.append(listToString(secondaryParam, isEqually));
        return result.toString();
    }

    private String listToString(List<String> list, boolean isEqually) {
        StringBuilder result = new StringBuilder();
        String eq = isEqually ? " = ?" : "";
        for (int i = 0; i < list.size(); i++)
            if (i == list.size() - 1) result.append(list.get(i) + eq);
            else result.append(list.get(i) + eq + ", ");
        return result.toString();
    }

    public void statementSetter(PreparedStatement statement, Object object, List<String> mainParam, List<String> secondaryParam, boolean isPattern) {
        if ((statement == null) || (object == null)) return;
        Map<String, Object[]> sourceMap = getSourceMap();
        mainParam = (mainParam == null) ? new ArrayList<>() : mainParam;
        if (secondaryParam != null) mainParam.addAll(secondaryParam);
        int count = 1;
        try {
            for (String param : mainParam) {
                Method method = object.getClass().getMethod((String) sourceMap.get(param)[0]);
                setValue(statement, count++, (TypeOfMethod) sourceMap.get(param)[1], method.invoke(object), isPattern);
            }
        } catch (NoSuchMethodException e) {
            logger.error("NoSuchMethodException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException: " + e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException: " + e.getMessage());
        }
    }

    private void setValue(PreparedStatement statement, int count, TypeOfMethod type, Object o, boolean isPattern) {
        if ((statement == null) || (count < 0) || (o == null)) return;
        try {
            switch (type) {
                case DATE:
                    statement.setDate(count, (Date) o);
                    return;
                case STRING:
                    statement.setString(count, isPattern ? "%" + o + "%" : (String) o);
                    return;
                case INT:
                    statement.setInt(count, (Integer) o);
            }
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
        }
    }
}
