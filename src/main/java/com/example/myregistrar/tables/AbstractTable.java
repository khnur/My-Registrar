package com.example.myregistrar.tables;

import com.example.myregistrar.util.DataBase;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.IntStream;

public abstract class AbstractTable<T> {
    private final String tableName;

    private final JdbcTemplate jdbcTemplate;

    private final List<String> columnNames;

    private final Class<T> clazz;

    private final BeanPropertyRowMapper<T> beanPropertyRowMapper;

    private long idIncrement = 0;

    protected AbstractTable(Class<T> clazz) {
        this.tableName = clazz.getSimpleName().toLowerCase();
        this.clazz = clazz;
        this.beanPropertyRowMapper = new BeanPropertyRowMapper<>(clazz);

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
        return jdbcTemplate.query(sql, beanPropertyRowMapper);
    }

    protected boolean existsByFirstAndSecond(String first, String second) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " +
                columnNames.get(1) + " = ? AND " + columnNames.get(2) + " = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, first, second);
        return count != null && count > 0;
    }

    protected List<T> findListByFirst(String first) {
        String sql = "SELECT * FROM " + tableName + " WHERE " +
                columnNames.get(1) + " = ?";

        return jdbcTemplate.query(sql, beanPropertyRowMapper, first);
    }

    protected List<T> findListBySecond(String second) {
        String sql = "SELECT * FROM " + tableName + " WHERE " +
                columnNames.get(2) + " = ?";
        return jdbcTemplate.query(sql, beanPropertyRowMapper, second);
    }

    protected Optional<T> findByFirstAndSecond(String first, String second) {
        String sql = "SELECT * FROM " + tableName + " WHERE " +
                columnNames.get(1) + " = ? AND " + columnNames.get(2) + " = ?";

        List<T> records = jdbcTemplate.query(sql, beanPropertyRowMapper, first, second);

        if (records.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(records.get(0));
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

    private boolean tableExists() {
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
            fieldValues[0] = idIncrement++;

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

    private final RowMapper<T> rowMapper = new RowMapper<>() {
        @Override
        public T mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            List<Object> results = new ArrayList<>();

            columnNames.forEach(col -> {
                if (!"id".equals(col)) {
                    try {
                        results.add(resultSet.getObject(col));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            Class<?>[] parameterTypes = new Class<?>[results.size()];

            IntStream.range(0, results.size())
                    .forEach(i -> parameterTypes[i] = results.get(i).getClass());

            try {
                return clazz.getDeclaredConstructor(parameterTypes).newInstance(results);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };
}
