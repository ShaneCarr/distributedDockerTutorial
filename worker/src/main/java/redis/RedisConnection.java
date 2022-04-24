package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisConnection {
        /*
     Connection to redis.

    */

    final String HOST = "redis";

    public Jedis connectToRedis() {
      return this.connectToRedis(HOST);
    }

    public Jedis connectToRedis(String host) {
        Jedis conn = new Jedis(host);

        while (true) {
          try {
            conn.keys("*");
            break;
          } catch (JedisConnectionException e) {
            System.err.println("Waiting for redis");
            sleep(1000);
          }
        }

        System.err.println("Connected to redis");
        return conn;
      }

      static void sleep(long duration) {
        try {
          Thread.sleep(duration);
        } catch (InterruptedException e) {
          System.exit(1);
        }
      }
}
