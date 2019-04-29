package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
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
                    case "EXPERIENCE": {
                        List<Company> listCompanies = ((CompanySection) sections).getCompanies();
                        int count = listCompanies.size();
                        dos.writeInt(count);
                        for (int i = 0; i < count; i++) {
                            Link link = listCompanies.get(i).getHomepage();
                            dos.writeUTF(link.getName());
                            dos.writeUTF(link.getUrl());
                            List<Company.Position> listPositions = listCompanies.get(i).getPositions();
                            int countPositions = listPositions.size();
                            dos.writeInt(countPositions);
                            for (int j = 0; j < countPositions; j++) {
                                dos.writeUTF(listPositions.get(j).getTitle());
                                dos.writeUTF(listPositions.get(j).getDescription());
                                //  String patternLocalDate="yyyy-MM-dd";
                                //   dos.writeUTF(patternLocalDate);
                                //   dos.writeUTF(listPositions.get(i).getStartDate().format(DateTimeFormatter.ofPattern(patternLocalDate)));
                                //  dos.writeUTF(listPositions.get(i).getEndDate().format(DateTimeFormatter.ofPattern(patternLocalDate)));
                                dos.writeInt(listPositions.get(j).getStartDate().getYear());
                                dos.writeInt(listPositions.get(j).getStartDate().getMonth().getValue());
                                dos.writeInt(listPositions.get(j).getEndDate().getYear());
                                dos.writeInt(listPositions.get(j).getEndDate().getMonth().getValue());
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
                    case "OBJECTIVE": {
                        resume.addSection(SectionType.OBJECTIVE, new SimpleTextSection(dis.readUTF()));
                    }
                    break;
                    case "PERSONAL": {
                        resume.addSection(SectionType.PERSONAL, new SimpleTextSection(dis.readUTF()));
                    }
                    break;
                    case "ACHIEVEMENT": {
                        List<String> listSection = getListSectionContent(dis);
                        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(listSection));
                    }
                    break;
                    case "QUALIFICATIONS": {
                        List<String> listSection = getListSectionContent(dis);
                        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(listSection));
                    }
                    break;
                    case "EXPERIENCE": {
                        int size = dis.readInt();
                        List<Company> listCompany = new ArrayList<>();
                        for (int j = 0; j < size; j++) {
                            String nameCompany = dis.readUTF();
                            String urlCompany = dis.readUTF();
                            int positionCount = dis.readInt();
                            List<Company.Position> listPosition = new ArrayList<>();
                            for (int k = 0; k < positionCount; k++) {
                                String titlePosition = dis.readUTF();
                                String titleDescription = dis.readUTF();
                                int startYear = dis.readInt();
                                Month startMonth = getMonth(dis.readInt());
                                int endYear = dis.readInt();
                                Month endMonth = getMonth(dis.readInt());
                                Company.Position positions = new Company.Position(titlePosition, titleDescription,
                                        startYear, startMonth, endYear, endMonth);
                                listPosition.add(positions);
                            }
                            resume.addSection(SectionType.EXPERIENCE, new CompanySection(
                                    new Company(new Link(nameCompany, urlCompany), listPosition)));

                        }
                    }
                }
            }
            return resume;
        }
    }

    private List<String> getListSectionContent(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> listSection = new ArrayList<>(size);
        for (int j = 0; j < size; j++) {
            listSection.add(dis.readUTF());
        }
        return listSection;
    }

    private Month getMonth(int numMonth) {
        switch (numMonth) {
            case 1:
                return Month.JANUARY;
            case 2:
                return Month.FEBRUARY;
            case 3:
                return Month.MARCH;
            case 4:
                return Month.APRIL;
            case 5:
                return Month.MAY;
            case 6:
                return Month.JUNE;
            case 7:
                return Month.JULY;
            case 8:
                return Month.AUGUST;
            case 9:
                return Month.SEPTEMBER;
            case 10:
                return Month.OCTOBER;
            case 11:
                return Month.NOVEMBER;
            case 12:
                return Month.DECEMBER;
        }
        return null;
    }


}

