package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.*;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String sqlQuery = "DELETE FROM resume";
        transactionExecute(new ABlockOfCode<Void>() {
            @Override
            public Void exec(PreparedStatement ps) throws SQLException {
                ps.execute();
                return null;
            }
        }, sqlQuery);
    }


    @Override
    public void update(Resume resume) {
        String sqlQuery = "UPDATE resume r SET full_name=? WHERE r.uuid=?";
        transactionExecute(new ABlockOfCode<Void>() {
            @Override
            public Void exec(PreparedStatement ps) throws SQLException {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                int count = ps.executeUpdate();
                if (count == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                return null;
            }
        }, sqlQuery);
    }


    @Override
    public void save(Resume resume) {
        String sqlQuery = "INSERT INTO resume(uuid,full_name) VALUES(?,?)";
        transactionExecute(new ABlockOfCode<Void>() {
            @Override
            public Void exec(PreparedStatement ps) throws SQLException {
                try {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.execute();
                } catch (PSQLException e) {
                    throw new ExistStorageException(resume.getUuid());
                }
                return null;
            }
        }, sqlQuery);
    }

    @Override
    public Resume get(String uuid) {
        String sqlQuery = "SELECT * FROM resume r WHERE r.uuid=?";
        return transactionExecute(new ABlockOfCode<Resume>() {
            @Override
            public Resume exec(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                return new Resume(uuid, rs.getString("full_name"));
            }
        }, sqlQuery);
    }

    @Override
    public void delete(String uuid) {
        String sqlQuery = "DELETE FROM resume r WHERE r.uuid=?";
        transactionExecute(new ABlockOfCode<Void>() {
            @Override
            public Void exec(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                int count = ps.executeUpdate();
                if (count == 0) {
                    throw new NotExistStorageException(uuid);
                }
                return null;
            }
        }, sqlQuery);
    }

    @Override
    public List<Resume> getAllSorted() {
        String sqlQuery = "SELECT * FROM resume order by full_name";
        return transactionExecute(new ABlockOfCode<List<Resume>>() {
            @Override
            public List<Resume> exec(PreparedStatement ps) throws SQLException {
                ResultSet rs = ps.executeQuery();
                List<Resume> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Resume(rs.getString(1).trim(), rs.getString(2)));
                }
                return list;
            }
        }, sqlQuery);
    }


    @Override
    public int size() {
        String sqlQuery = "SELECT COUNT(*) AS count FROM resume";
        return transactionExecute(new ABlockOfCode<Integer>() {
            @Override
            public Integer exec(PreparedStatement ps) throws SQLException {
                ResultSet rs = ps.executeQuery();
                int count = 0;
                while (rs.next()) {
                    count = rs.getInt(1);
                }
                return count;
            }
        }, sqlQuery);
    }


    public interface ABlockOfCode<T> {
        T exec(PreparedStatement ps) throws SQLException;
    }

    private <T> T transactionExecute(ABlockOfCode<T> aBlockOfCode, String query) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return aBlockOfCode.exec(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }


}
