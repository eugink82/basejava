package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class SqlHelper {
    public ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String query) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T execute(String query, SqlExecutor<T> sqlExecutor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return sqlExecutor.exec(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> transactExecutor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = transactExecutor.exec(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
