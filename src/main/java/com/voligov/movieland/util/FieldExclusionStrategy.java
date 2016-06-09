package com.voligov.movieland.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.util.List;

class FieldExclusionStrategy implements ExclusionStrategy {
    private final List<String> includeFields;

    public FieldExclusionStrategy(List<String> includeFields) {
        this.includeFields = includeFields;
    }

    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes attributes) {
        return !includeFields.contains(attributes.getName());
    }
}
