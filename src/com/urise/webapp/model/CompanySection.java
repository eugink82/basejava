package com.urise.webapp.model;

import java.util.List;

public class CompanySection implements Sections {
    private List<Company> listCompany;

    public CompanySection(List<Company> listCompany) {
        this.listCompany = listCompany;
    }

    public List<Company> getListCompany() {
        return listCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return listCompany != null ? listCompany.equals(that.listCompany) : that.listCompany == null;
    }

    @Override
    public int hashCode() {
        return listCompany != null ? listCompany.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Company section : listCompany)
            sb.append(section).append(System.lineSeparator());
        return sb.toString();
    }
}
