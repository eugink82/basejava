package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection implements Sections {
    private final List<String> list;

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "список секций не должен быть пустым");
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String section : list)
            sb.append("-").append(section).append(System.lineSeparator());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
