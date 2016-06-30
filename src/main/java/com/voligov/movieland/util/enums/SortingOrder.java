package com.voligov.movieland.util.enums;

public enum SortingOrder {
    ASC("asc"), DESC("desc");

    private String sortOrder;

    SortingOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public static SortingOrder getBySortString(String sortOrder) {
        for (SortingOrder s : SortingOrder.values()) {
            if (s.sortOrder.equals(sortOrder)) {
                return s;
            }
        }
        return null;
    }
}
