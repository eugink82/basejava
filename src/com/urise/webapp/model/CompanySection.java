package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection implements Sections {
    private final List<Company> listCompany;

    public CompanySection(List<Company> listCompany) {
        Objects.requireNonNull(listCompany, "Поле \"Организация\" не должно быть пустым");
        this.listCompany = listCompany;
    }

    public List<Company> getListCompany() {
        return listCompany;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Company section : listCompany)
            sb.append(section).append(System.lineSeparator());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return listCompany.equals(that.listCompany);
    }

    @Override
    public int hashCode() {
        return listCompany.hashCode();
    }
}
