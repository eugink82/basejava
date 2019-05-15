package com.urise.webapp.util;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;
import java.sql.*;

public class SqlHelper {
    public ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T transactionExecute(ABlockOfCode<T> aBlockOfCode, String query) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return aBlockOfCode.exec(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
