package org.shop.utils.table;

import java.util.HashSet;
import java.util.Set;

public class TableConfig {
    private final Set<String> excludeFields = new HashSet<>();

    public TableConfig(String fieldName) {
        this.excludeField(fieldName);
    }

    public TableConfig(Set<String> fieldNames) {
        this.excludeFields(fieldNames);
    }

    public void excludeField(String fieldName) {
        excludeFields.add(fieldName);
    }

    public void excludeFields(Set<String> fieldNames) {
        excludeFields.addAll(fieldNames);
    }

    public boolean isExcluded(String fieldName) {
        return excludeFields.contains(fieldName);
    }
}
