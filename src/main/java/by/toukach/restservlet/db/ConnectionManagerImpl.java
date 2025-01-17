package by.toukach.restservlet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManagerImpl implements ConnectionManager {

  private static final String DRIVER_CLASS_KEY = "org.postgresql.Driver";
  private static final String URL_KEY = "db.url";
  private static final String USERNAME_KEY = "db.username";
  private static final String PASSWORD_KEY = "db.password";

  private static ConnectionManager instance;

  private ConnectionManagerImpl() {
  }

  public static synchronized ConnectionManager getInstance() {
    if (instance == null) {
      instance = new ConnectionManagerImpl();
      loadDriver(PropertiesUtil.getProperties(DRIVER_CLASS_KEY));
    }
    return instance;
  }

  private static void loadDriver(String driverClass) {
    try {
      Class.forName(DRIVER_CLASS_KEY);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        PropertiesUtil.getProperties(URL_KEY),
        PropertiesUtil.getProperties(USERNAME_KEY),
        PropertiesUtil.getProperties(PASSWORD_KEY)
    );
  }

}
