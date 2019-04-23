package com.urise.webapp.model;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.urise.webapp.util.*;

public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link homepage;
    private List<Position> positions = new ArrayList<>();

    public Company(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Company(Link homepage, List<Position> position) {
        this.homepage = homepage;
        this.positions = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(homepage, company.homepage) &&
                Objects.equals(positions, company.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, positions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Position p : positions)
            sb.append(p.toString()).append(System.lineSeparator());

        return homepage.getName() + System.lineSeparator() + sb.toString();
    }

    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String title;
        private final String description;
        private final LocalDate startDate;
        private final LocalDate endDate;

        public Position(String title, String description, int startYear, Month startMonth) {
            this(title, description, DateUtil.of(startYear, startMonth), DateUtil.NOW);
        }

        public Position(String title, String description, int startYear, Month startMonth, int endYear, Month endMonth) {
            this(title, description, DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth));
        }

        public Position(String title, String description, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(title, "Позиция работы, учебы не должно быть пустым");
            Objects.requireNonNull(startDate, "Начало работы не должно быть пустым");
            Objects.requireNonNull(endDate, "Конец работы не должно быть пустым");

            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description) &&
                    Objects.equals(startDate, position.startDate) &&
                    Objects.equals(endDate, position.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, description, startDate, endDate);
        }

        @Override
        public String toString() {
            String datePattern = "MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
            return dateFormatter.format(startDate) + "-" + dateFormatter.format(endDate) + "  " +
                    title + System.lineSeparator() + "                 " + description;
        }
    }
}
