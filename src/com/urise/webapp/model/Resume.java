package com.urise.webapp.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;
    public static final Resume EMPTY = new Resume();

    static {
        EMPTY.addSection(SectionType.OBJECTIVE, SimpleTextSection.EMPTY);
        EMPTY.addSection(SectionType.PERSONAL, SimpleTextSection.EMPTY);
        EMPTY.addSection(SectionType.ACHIEVEMENT, ListSection.EMPTY);
        EMPTY.addSection(SectionType.QUALIFICATIONS, ListSection.EMPTY);
        EMPTY.addSection(SectionType.EXPERIENCE, CompanySection.EMPTY);
        EMPTY.addSection(SectionType.EDUCATION, CompanySection.EMPTY);
    }

    // Unique identifier
    private String uuid;
    private String fullName;
    public Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    public Map<SectionType, Sections> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Sections> getSections() {
        return sections;
    }

    public String getContact(ContactType cType) {
        return contacts.get(cType);
    }

    public Sections getSection(SectionType s) {
        return sections.get(s);
    }

    public void addSection(SectionType type, Sections section) {
        sections.put(type, section);
    }

    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int resultCompare = fullName.compareTo(o.getFullName());
        return resultCompare != 0 ? resultCompare : uuid.compareTo(o.getUuid());
    }
}
