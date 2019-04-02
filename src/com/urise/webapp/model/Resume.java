package com.urise.webapp.model;

import java.util.*;
import java.io.*;
import java.util.Map.Entry;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    public Map<SectionType, Sections> mapSections = new EnumMap<>(SectionType.class);
    public Map<ContactType, String> mapContacts = new EnumMap<>(ContactType.class);

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
        return mapSections.get(s);
    }

    public String getContact(ContactType cType) {
        return mapContacts.get(cType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        if (fullName != null ? !fullName.equals(resume.fullName) : resume.fullName != null) return false;
        if (mapSections != null ? !mapSections.equals(resume.mapSections) : resume.mapSections != null) return false;
        return mapContacts != null ? mapContacts.equals(resume.mapContacts) : resume.mapContacts == null;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (mapSections != null ? mapSections.hashCode() : 0);
        result = 31 * result + (mapContacts != null ? mapContacts.hashCode() : 0);
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

    public void addContacts() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (ContactType c : ContactType.values()) {
            System.out.print("Введите " + c.getTittle() + ": ");
            mapContacts.put(c, reader.readLine());
        }
    }

    public void printContacts() {
        System.out.println(fullName);
        for (Map.Entry<ContactType, String> m : mapContacts.entrySet()) {
            System.out.println(m.getKey() + ": " + m.getValue());
        }
    }

}
