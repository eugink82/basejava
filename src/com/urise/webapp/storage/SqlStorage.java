package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.urise.webapp.util.ABlockOfCode;
import com.urise.webapp.util.SqlHelper;
import org.postgresql.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        String sqlQuery = "DELETE FROM resume";
        sqlHelper.transactionExecute((ABlockOfCode<Void>) ps -> {
            ps.execute();
            return null;
        }, sqlQuery);
    }


    @Override
    public void update(Resume resume) {
        String sqlQuery = "UPDATE resume r SET full_name=? WHERE r.uuid=?";
        sqlHelper.transactionExecute((ABlockOfCode<Void>) ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        }, sqlQuery);
    }


    @Override
    public void save(Resume resume) {
        String sqlQuery = "INSERT INTO resume(uuid,full_name) VALUES(?,?)";
        sqlHelper.transactionExecute((ABlockOfCode<Void>) ps -> {
          try {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
              } catch (PSQLException e) {
                 if(e.getSQLState().equals("23505"))
                      throw new ExistStorageException(resume.getUuid());
             }
            return null;
        }, sqlQuery);
    }

    @Override
    public Resume get(String uuid) {
        String sqlQuery = "SELECT * FROM resume r WHERE r.uuid=?";
        return sqlHelper.transactionExecute(ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }, sqlQuery);
    }

    @Override
    public void delete(String uuid) {
        String sqlQuery = "DELETE FROM resume r WHERE r.uuid=?";
        sqlHelper.transactionExecute((ABlockOfCode<Void>) ps -> {
            ps.setString(1, uuid);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, sqlQuery);
    }

    @Override
    public List<Resume> getAllSorted() {
        String sqlQuery = "SELECT * FROM resume order by full_name";
        return sqlHelper.transactionExecute(ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString(1).trim(), rs.getString(2)));
            }
            return list;
        }, sqlQuery);
    }


    @Override
    public int size() {
        String sqlQuery = "SELECT COUNT(*) AS count FROM resume";
        return sqlHelper.transactionExecute(ps -> {
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        }, sqlQuery);
    }
}
