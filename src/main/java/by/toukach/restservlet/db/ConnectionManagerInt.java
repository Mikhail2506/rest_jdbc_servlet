package by.toukach.restservlet.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManagerInt {
    Connection open() throws SQLException;
}
