package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection implements Sections {
    private List<String> list;

    public ListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public void printSection() {
        for(String s: list){
            System.out.println("-"+s);
        }
    }
}
