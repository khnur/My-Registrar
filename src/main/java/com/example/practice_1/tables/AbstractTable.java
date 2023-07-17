package com.example.practice_1.tables;

import com.example.practice_1.util.DataBase;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public abstract class AbstractTable<T> {
    protected final String tableName;
    protected final Class<T> clazz;
    protected final JdbcTemplate jdbcTemplate;
    protected final Map<String, T> map = new HashMap<>();

    private final List<String> columnNames;

    protected AbstractTable(Class<T> clazz) {
        this.tableName = clazz.getSimpleName().toLowerCase();
        this.clazz = clazz;

        try {
            jdbcTemplate = new JdbcTemplate(DataBase.getDataSource());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("DataSource Not Found");
        }

        columnNames = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> getColumnType(field.getType()) != null)
                .map(Field::getName)
                .toList();

        createTable();
    }

    public List<T> findAll() {
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz));
    }

    protected boolean existsByFirstAndSecond(String first, String second) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " +
                columnNames.get(1) + " = ? AND " + columnNames.get(2) + " = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, first, second);

        System.out.println(sql);
        System.out.println(count);
        System.out.println();

        return count != null && count > 0;
    }

    protected List<T> findListByFirst(String first) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(first.toLowerCase() + "#"))
                .map(Map.Entry::getValue)
                .toList();
    }

    protected List<T> findListBySecond(String second) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().endsWith("#" + second.toLowerCase()))
                .map(Map.Entry::getValue)
                .toList();
    }

    protected Optional<T> findByFirstAndSecond(String first, String second) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().equals(toHash(first, second)))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    protected String toHash(String first, String second) {
        return first.toLowerCase() + "#" + second.toLowerCase();
    }

    private void createTable() {
        if (tableExists()) return;

        StringBuilder createTableSql = new StringBuilder();

        createTableSql.append("CREATE TABLE ").append(tableName).append(" (");

        List<String> columnDefinitions = getColumnDefinitionsFromClass();
        createTableSql.append(String.join(", ", columnDefinitions));

        createTableSql.append(");");

        jdbcTemplate.execute(createTableSql.toString());
    }

    private boolean tableExists() { ////
        String sql = "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";

        List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, tableName);

        return !result.isEmpty();
    }

    private List<String> getColumnDefinitionsFromClass() {
        List<String> columnDefinitions = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String columnName = field.getName();
            String columnType = getColumnType(field.getType());

            if (columnType == null) continue;

            columnDefinitions.add(columnName + " " + columnType);
        }

        return columnDefinitions;
    }

    private String getColumnType(Class<?> type) {
        if (type == String.class) {
            return "VARCHAR(255) NOT NULL";
        } else if (type == int.class) {
            return "INT";
        } else if (type == Integer.class) {
            return "INT NOT NULL";
        } else if (type == long.class) {
            return "BIGINT";
        } else if (type == Long.class) {
            return "BIGINT NOT NULL";
        } else if (type == boolean.class || type == Boolean.class) {
            return "BOOLEAN";
        } else if (type == Date.class) {
            return "DATE NOT NULL";
        } else {
            return null;
        }
    }

    protected void save(T object, String firstVal, String secondVal) {
        StringBuilder sql = new StringBuilder();

        if (existsByFirstAndSecond(firstVal, secondVal)) {
            sql.append("UPDATE ").append(tableName).append(" SET ");
            sql.append(String.join(", ", columnNames.stream()
                    .map(column -> column + " = ?")
                    .toList()
            ));

            sql.append(" WHERE ").append(columnNames.get(1)).append(" = ? AND ").append(columnNames.get(2)).append(" = ?");


            Object[] fieldValues = getObjects(object);

            Object[] params = Arrays.copyOf(fieldValues, fieldValues.length + 2);
            params[fieldValues.length] = firstVal;
            params[fieldValues.length + 1] = secondVal;

            jdbcTemplate.update(sql.toString(), params);

        } else {
            sql.append("INSERT INTO ").append(tableName).append(" VALUES ").append(" (")
                    .append(String.join(", ", columnNames.stream()
                            .map(col -> "?")
                            .toList()
                    )).append(")");

            Object[] fieldValues = getObjects(object);

            System.out.println(sql);
            System.out.println(Arrays.toString(fieldValues));

            jdbcTemplate.update(sql.toString(), fieldValues);
        }
    }

    private Object[] getObjects(T object) {
        return columnNames.stream()
                .map(column -> {
                    String getterMethodName = "get" + column.substring(0, 1).toUpperCase() + column.substring(1);
                    try {
                        Method getterMethod = object.getClass().getMethod(getterMethodName);
                        return getterMethod.invoke(object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .toArray();
    }
}
