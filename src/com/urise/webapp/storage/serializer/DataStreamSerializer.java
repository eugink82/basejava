package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.util.Map;
import java.util.logging.Logger;
import java.util.*;
import java.time.*;

public class DataStreamSerializer implements StorageStrategy {
    private static final Logger LOG = Logger.getLogger(DataStreamSerializer.class.getName());

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        LOG.info("Cериализация с " + DataStreamSerializer.class.getSimpleName());
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            }

            dos.writeInt(resume.getSections().size());
            for (Map.Entry<SectionType, Sections> section : resume.getSections().entrySet()) {
                String sectionType = section.getKey().name();
                Sections sections = section.getValue();
                dos.writeUTF(sectionType);
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL": {
                        dos.writeUTF(((SimpleTextSection) sections).getContent());
                    }
                    break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS": {
                        List<String> listContent = ((ListSection) sections).getList();
                        int count = listContent.size();
                        dos.writeInt(count);
                        for (int i = 0; i < count; i++) {
                            dos.writeUTF(listContent.get(i));
                        }
                    }
                    break;
                    case "EXPERIENCE":
                    case "EDUCATION": {
                        List<Company> listCompanies = ((CompanySection) sections).getCompanies();
                        int count = listCompanies.size();
                        dos.writeInt(count);
                        for (int i = 0; i < count; i++) {
                            Link link = listCompanies.get(i).getHomepage();
                            dos.writeUTF(link.getName());
                            String url = (link.getUrl() != null) ? link.getUrl() : "";
                            dos.writeUTF(url);
                            List<Company.Position> listPositions = listCompanies.get(i).getPositions();
                            int countPositions = listPositions.size();
                            dos.writeInt(countPositions);
                            for (int j = 0; j < countPositions; j++) {
                                Company.Position positionItem = listPositions.get(j);
                                dos.writeUTF(positionItem.getTitle());
                                String description = (sectionType.equals("EDUCATION")) ? "" : positionItem.getDescription();
                                dos.writeUTF(description);
                                DateSerialization(dos, positionItem.getStartDate());
                                DateSerialization(dos, positionItem.getEndDate());
                            }
                        }
                    }
                }
            }

        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int countC = dis.readInt();
            for (int i = 0; i < countC; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                String sectionName = dis.readUTF();
                switch (sectionName) {
                    case "OBJECTIVE":
                    case "PERSONAL": {
                        resume.addSection(SectionType.valueOf(sectionName), new SimpleTextSection(dis.readUTF()));
                    }
                    break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS": {
                        List<String> listSection = getListSectionContent(dis);
                        resume.addSection(SectionType.valueOf(sectionName), new ListSection(listSection));
                    }
                    break;
                    case "EXPERIENCE":
                    case "EDUCATION": {
                        List<Company> listCompany = getListCompanyContent(dis, sectionName);
                        resume.addSection(SectionType.valueOf(sectionName), new CompanySection(
                                listCompany));
                    }
                }
            }
            return resume;
        }
    }

    private List<Company> getListCompanyContent(DataInputStream dis, String sectionName) throws IOException {
        int size = dis.readInt();
        List<Company> listCompany = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            String nameCompany = dis.readUTF();
            String urlCompany = dis.readUTF();
            urlCompany=(urlCompany.equals("")) ? null : urlCompany;
            int positionCount = dis.readInt();
            List<Company.Position> listPosition = new ArrayList<>();
            for (int k = 0; k < positionCount; k++) {
                String titlePosition = dis.readUTF();
                String titleDescription = dis.readUTF();
                titleDescription = (sectionName.equals("EDUCATION")) ? null : titleDescription;
                LocalDate startDate = DateDeserialization(dis);
                LocalDate endDate = DateDeserialization(dis);
                Company.Position positions = new Company.Position(titlePosition, titleDescription,
                        startDate, endDate);
                listPosition.add(positions);
            }
            listCompany.add(new Company(new Link(nameCompany, urlCompany), listPosition));

        }
        return listCompany;
    }

    private List<String> getListSectionContent(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> listSection = new ArrayList<>(size);
        for (int j = 0; j < size; j++) {
            listSection.add(dis.readUTF());
        }
        return listSection;
    }

    private void DateSerialization(DataOutputStream dos, LocalDate dt) throws IOException {
        dos.writeInt(dt.getYear());
        dos.writeInt(dt.getMonth().getValue());
    }

    private LocalDate DateDeserialization(DataInputStream dis) throws IOException {
        int year = dis.readInt();
        Month month = Month.of(dis.readInt());
        return DateUtil.of(year, month);
    }
}

