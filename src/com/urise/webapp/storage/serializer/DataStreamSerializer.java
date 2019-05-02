package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.util.Map;
import java.util.function.Consumer;
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

            writeWithException(resume.getContacts().entrySet(), dos, contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });

            writeWithException(resume.getSections().entrySet(), dos, section -> {
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
                        writeWithException(listContent, dos, dos::writeUTF);
                    }
                    break;
                    case "EXPERIENCE":
                    case "EDUCATION": {
                        List<Company> listCompanies = ((CompanySection) sections).getCompanies();
                        writeWithException(listCompanies, dos, elemCompanySection -> {
                            Link link = elemCompanySection.getHomepage();
                            dos.writeUTF(link.getName());
                            String url = (link.getUrl() != null) ? link.getUrl() : "";
                            dos.writeUTF(url);
                            List<Company.Position> listPositions = elemCompanySection.getPositions();
                            writeWithException(listPositions, dos, elemPosition -> {
                                dos.writeUTF(elemPosition.getTitle());
                                String description = (elemPosition.getDescription() != null) ? elemPosition.getDescription() : "";
                                dos.writeUTF(description);
                                dateSerialization(dos, elemPosition.getStartDate());
                                dateSerialization(dos, elemPosition.getEndDate());
                            });
                        });
                    }
                }
            });
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readWithException(dis, () -> {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });

            readWithException(dis, () -> {
                String sectionName = dis.readUTF();
                SectionType sectionType = SectionType.valueOf(sectionName);
                switch (sectionName) {
                    case "OBJECTIVE":
                    case "PERSONAL": {
                        resume.addSection(sectionType, new SimpleTextSection(dis.readUTF()));
                    }
                    break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS": {
                        List<String> listSection = getListSectionContent(dis);
                        resume.addSection(sectionType, new ListSection(listSection));
                    }
                    break;
                    case "EXPERIENCE":
                    case "EDUCATION": {
                        List<Company> listCompany = getListCompanyContent(dis, sectionName);
                        resume.addSection(sectionType, new CompanySection(
                                listCompany));
                    }
                }
            });
            return resume;
        }
    }

    private interface MyConsumerWriter<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, MyConsumerWriter<T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T elem : collection) {
            action.write(elem);
        }
    }

    private interface MyConsumerReader {
        void read() throws IOException;
    }

    private <T> void readWithException(DataInputStream dis, MyConsumerReader action) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            action.read();
        }
    }

    private interface AddToList<T> {
        T read() throws IOException;
    }

    private <T> List<T> readToListWithException(DataInputStream dis, AddToList<T> action) throws IOException{
        int count=dis.readInt();
        List<T> list=new ArrayList<>(count);
        for(int i=0;i<count;i++){
            list.add(action.read());
        }
        return list;
    }

    private List<Company> getListCompanyContent(DataInputStream dis, String sectionName) throws IOException {
        return readToListWithException(dis,()-> {
            String nameCompany = dis.readUTF();
            String urlCompany = dis.readUTF();
            urlCompany = (urlCompany.equals("")) ? null : urlCompany;
            return  new Company(new Link(nameCompany, urlCompany), readToListWithException(dis, () -> {
                String titlePosition = dis.readUTF();
                String titleDescription = dis.readUTF();
                titleDescription = (titleDescription.equals("")) ? null : titleDescription;
                LocalDate startDate = dateSerialization(dis);
                LocalDate endDate = dateSerialization(dis);
                return new Company.Position(titlePosition, titleDescription, startDate, endDate);
            }));
        });
    }

    private List<String> getListSectionContent(DataInputStream dis) throws IOException {
        return readToListWithException(dis, dis::readUTF);
    }

    private void dateSerialization(DataOutputStream dos, LocalDate dt) throws IOException {
        dos.writeInt(dt.getYear());
        dos.writeInt(dt.getMonth().getValue());
    }

    private LocalDate dateSerialization(DataInputStream dis) throws IOException {
        int year = dis.readInt();
        Month month = Month.of(dis.readInt());
        return DateUtil.of(year, month);
    }
}

