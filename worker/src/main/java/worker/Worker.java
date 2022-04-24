package worker;

import redis.RedisConnection;
import redis.clients.jedis.Jedis;
import java.sql.*;
import org.json.JSONObject;

import data.Vote;
import data.schema.Schema;
import postgres.Databaseconnector;

// going to create a service /background service
// background working /schedule task
// use jetty to add real service endpoints.
public class Worker {
    private static Jedis reddisConnection;

    public static void main(String[] args) throws SQLException {
        System.out.println("Starting worker");

        Databaseconnector conn = new Databaseconnector();
        Connection schemaConnection = conn.connectToDB();

        Schema schema = new Schema(schemaConnection);
        schema.CreateDatabase();

        System.out.println("Watching vote queue");

        RedisConnection redisConnector = new RedisConnection();
        reddisConnection = redisConnector.connectToRedis();

        Connection workerConnection = conn.connectToDB();
        Vote vote = new Vote();

        while (true) {
          String voteJSON = reddisConnection.blpop(0, "votes").get(1);
          JSONObject voteData = new JSONObject(voteJSON);
          String voterID = voteData.getString("voter_id");
          String voteInfo = voteData.getString("vote");
          System.err.printf("Processing vote for '%s' by '%s'\n", vote, voterID);
          vote.UpdateVote(workerConnection, voterID, voteInfo);
        }

    }

    // this will be schedule executor

}
