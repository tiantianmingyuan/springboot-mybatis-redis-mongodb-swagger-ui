
package com.zkzn.payment.server.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;



@Component
public class RedisPool {
    
    @Autowired
    private JedisPool jedisPool;

    public Jedis getConnect() throws JedisConnectionException {

        return this.jedisPool.getResource();
    }

    public Jedis getConnect(int dbIndex) throws JedisConnectionException {
        Jedis jedis = this.getConnect();
        jedis.select(dbIndex);
        return jedis;
    }

    public void release(Jedis jedis) throws JedisException {
    	if(jedis != null) {
    	    jedis.close();
    		jedis = null;
    	}
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    
}
