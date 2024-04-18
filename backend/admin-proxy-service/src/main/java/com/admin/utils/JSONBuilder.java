package com.admin.utils;

import java.util.*;

public class JSONBuilder {
    private Map<String, Object> map;

    public JSONBuilder() {
        map = new HashMap<>();
    }

    public JSONBuilder addAttribute(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return map;
    }
}
