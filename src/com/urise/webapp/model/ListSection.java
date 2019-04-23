package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection implements Sections {
    private static final long serialVersionUID = 1L;

    private final List<String> items;

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "список секций не должен быть пустым");
        this.items = items;
    }

    public List<String> getList() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String section : items)
            sb.append("-").append(section).append(System.lineSeparator());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
