package data.schema;

import java.sql.Connection;
import java.sql.SQLException;

public class Schema {
    private final Connection conn;

    // temporary. use a frameowrk for this. 
    public Schema(Connection conn) {
      this.conn = conn;
    }

    public void CreateDatabase() throws SQLException {
        try {
            new VoteSchema(conn).Create();
            
        } catch (SQLException e) {
            // add logger
            System.err.println("creating database failed");
            throw e;
        }
    }
}
