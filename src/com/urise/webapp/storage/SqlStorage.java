package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;

import java.sql.*;
import java.util.*;

import com.urise.webapp.sql.SqlHelper;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        String sqlQuery = "DELETE FROM resume";
        sqlHelper.execute(sqlQuery);
    }


    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name=? WHERE r.uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                int count = ps.executeUpdate();
                if (count == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContact(resume, conn);
            insertContact(resume, conn);
            deleteSection(resume, conn);
            insertSection(resume, conn);
            return null;
        });

    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid,full_name) VALUES(?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContact(resume, conn);
            insertSection(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            String sqlResumeQuery = "SELECT * FROM resume r WHERE r.uuid=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlResumeQuery)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
            }
            String sqlContactQuery = "SELECT * FROM contact c WHERE c.resume_uuid=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlContactQuery)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(resume, rs);
                }
            }
            String sqlSectionQuery = "SELECT * FROM section s WHERE s.resume_uuid=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlSectionQuery)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resume, rs);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        String sqlDeleteQuery="DELETE FROM resume r WHERE r.uuid=?";
        sqlHelper.execute(sqlDeleteQuery,ps -> {
            ps.setString(1,uuid);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            String sqlResumeQuery = "SELECT * FROM resume ORDER BY full_name,uuid";
            try (PreparedStatement ps = conn.prepareStatement(sqlResumeQuery)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            String sqlContactQuery = "SELECT * FROM contact";
            try (PreparedStatement ps = conn.prepareStatement(sqlContactQuery)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(resumes.get(rs.getString("resume_uuid")), rs);
                }
            }
            String sqlSectionQuery = "SELECT * FROM section";
            try (PreparedStatement ps = conn.prepareStatement(sqlSectionQuery)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resumes.get(rs.getString("resume_uuid")), rs);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }


    @Override
    public int size() {
        String sqlQuery = "SELECT COUNT(*) AS count FROM resume";
        return sqlHelper.execute(sqlQuery, ps -> {
            ResultSet rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        });
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact(resume_uuid,type,value) VALUES(?,?,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void deleteContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact where resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }


    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section(resume_uuid,type,content) VALUES(?,?,?)")) {
            for (Map.Entry<SectionType, Sections> section : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                String sectionType = section.getKey().name();
                ps.setString(2, sectionType);
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL": {
                        ps.setString(3, section.getValue().toString());
                    }
                    break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS": {
                        List<String> content = ((ListSection) section.getValue()).getList();
                        String joinedContent = String.join("\n", content);
                        ps.setString(3, joinedContent);
                    }
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String sectionType = rs.getString("type");
        String content = rs.getString("content");
        if (content != null) {
            switch (sectionType) {
                case "OBJECTIVE":
                case "PERSONAL": {
                    resume.addSection(SectionType.valueOf(sectionType), new SimpleTextSection(content));
                }
                break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS": {
                    String[] listContent = content.split("\n");
                    resume.addSection(SectionType.valueOf(sectionType), new ListSection(listContent));
                }
            }
        }
    }

    private void deleteSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section where resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }
}
