package com.urise.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    public Map<SectionType, Sections> sections = new EnumMap<>(SectionType.class);
    public Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid поле не должно быть пустым");
        Objects.requireNonNull(fullName, "fullName поле не должно быть пустым");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Sections getSection(SectionType s) {
        return sections.get(s);
    }

    public String getContact(ContactType cType) {
        return contacts.get(cType);
    }

    public void addSection(SectionType type, Sections section) {
        sections.put(type,section);
    }

    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        int resultCompare = fullName.compareTo(o.getFullName());
        return resultCompare != 0 ? resultCompare : uuid.compareTo(o.getUuid());
    }

}
