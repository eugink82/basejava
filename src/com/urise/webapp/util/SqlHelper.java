package com.urise.webapp.util;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.storage.SqlStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
    public ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    public SqlHelper(){}



    public <T> T transactionExecute(ABlockOfCode<T> aBlockOfCode, String query) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return aBlockOfCode.exec(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
