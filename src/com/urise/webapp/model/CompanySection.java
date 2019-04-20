package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompanySection implements Sections {
    private final List<Company> companies;

    public CompanySection(Company... companies){
        this(Arrays.asList(companies));
    }

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "Поле \"Организация\" не должно быть пустым");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Company section : companies)
            sb.append(section).append(System.lineSeparator());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }
}
