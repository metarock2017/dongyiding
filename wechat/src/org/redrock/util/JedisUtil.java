package org.redrock.util;

import redis.clients.jedis.Jedis;

/**
 * Created by jx on 2017/7/21.
 */
public class JedisUtil {

    private static Jedis jedis;

    public static Jedis getJedis() {
        return new Jedis("localhost");
    }

/*    public static void main(String[] args) {
        Jedis jedis = getJedis();
        jedis.expire(Const.KeyOfAccessToken, 2);
    }*/
}
