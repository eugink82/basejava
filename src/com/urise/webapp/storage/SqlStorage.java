package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;

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
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact where resume_uuid=?")) {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
            }
            insertContact(resume, conn);
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
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String sqlQuery = " SELECT * FROM resume r " +
                "  LEFT JOIN contact c " +
                "     ON r.uuid=c.resume_uuid " +
                "  WHERE r.uuid=? ";
        return sqlHelper.execute(sqlQuery, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                addContact(r, rs);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume r WHERE r.uuid=?")) {
                ps.setString(1, uuid);
                int count = ps.executeUpdate();
                if (count == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String sqlQuery = "SELECT * FROM resume r LEFT JOIN contact c on r.uuid=c.resume_uuid\n" +
                "ORDER BY full_name,uuid";
        return sqlHelper.execute(sqlQuery, ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> map=new LinkedHashMap<>();
            Resume resume=null;
            while(rs.next()){
                String uuid=rs.getString("uuid");
                resume=map.get(uuid);
                if(resume==null){
                    resume = new Resume(uuid, rs.getString("full_name"));
                    map.put(uuid,resume);
                }
                addContact(resume,rs);
            }
            return new ArrayList<>(map.values());
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
}
