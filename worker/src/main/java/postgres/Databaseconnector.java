package postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Databaseconnector {
    final String HOST = "db";

    public Connection connectToDB() throws SQLException {
        return connectToDB(HOST);
    }

    public Connection connectToDB(String host) throws SQLException {
        Connection conn = null;
        try {
          Class.forName("org.postgresql.Driver");
          String url = "jdbc:postgresql://" + host + "/postgres";
          while (conn == null) {
            try {
              conn = DriverManager.getConnection(url, "postgres", "postgres");
            } catch (SQLException e) {
              System.err.println("Waiting for db");
              sleep(1000);
            }
          }
          PreparedStatement st = conn.prepareStatement(
            "CREATE TABLE IF NOT EXISTS votes (id VARCHAR(255) NOT NULL UNIQUE, vote VARCHAR(255) NOT NULL)");
          st.executeUpdate();
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
          System.exit(1);
        }
        System.err.println("Connected to db");
        return conn;
      }

      // better retry
      static void sleep(long duration) {
        try {
          Thread.sleep(duration);
        } catch (InterruptedException e) {
          System.exit(1);
        }
      }
}
