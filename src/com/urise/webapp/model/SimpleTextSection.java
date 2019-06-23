package com.urise.webapp.model;

import java.util.*;

public class SimpleTextSection extends Sections {
    private static final long serialVersionUID = 1L;
    public static SimpleTextSection EMPTY = new SimpleTextSection("");

    private String content;

    public SimpleTextSection() {
    }

    public SimpleTextSection(String content) {
        Objects.requireNonNull(content, "Контент не должен быть пустым");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTextSection that = (SimpleTextSection) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
