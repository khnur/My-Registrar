package com.example.practice_1.tables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractTable<T> {
    protected final Map<String, T> map = new HashMap<>();

    public List<T> findAll() {
        return map.values().stream().toList();
    }

    protected boolean existsByFistAndSecond(String first, String second) {
        return map.containsKey(toHash(first, second));
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
}
