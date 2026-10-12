package com.campus.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReportRow {
    private final Map<String, String> values = new LinkedHashMap<>();

    public void put(String key, String value) {
        values.put(key, value);
    }

    public Map<String, String> getValues() {
        return values;
    }
}
