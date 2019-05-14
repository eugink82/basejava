package com.urise.webapp.sql;

import java.sql.*;

public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}
