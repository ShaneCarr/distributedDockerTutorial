package data.schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VoteSchema {

  private final String CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS votes (id VARCHAR(255) NOT NULL UNIQUE, vote VARCHAR(255) NOT NULL)";
  private final Connection conn;

  // temporary. use a framework for this.
  public VoteSchema(Connection conn) {
    this.conn = conn;
  }

  final String TableName = "votes";

  public void Create() throws SQLException {
    PreparedStatement st = conn.prepareStatement(CREATE_STATEMENT);
    st.executeUpdate();
  }

}