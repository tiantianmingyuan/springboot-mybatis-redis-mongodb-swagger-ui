package com.zkzn.payment.server.util.redis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import redis.clients.jedis.Jedis;

@Component
public class RedisCommonCmd {

    @Autowired
    private RedisPool redisPool;
    
    public void setRedisPool(RedisPool redisPool) {
        this.redisPool = redisPool;
    }
    
    public boolean set(int dbIndex,int expireTime,String key,String value) {
        Jedis jedis = null;
        try {
            if (StringUtils.isBlank(key)) {
                return false;
            }
            jedis = redisPool.getConnect(dbIndex);
            jedis.set(key,value);
            // 设置过期时间
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return true;
    }
    
    public boolean set(int dbIndex,String key,String value) {
        Jedis jedis = null;
        try {
            if (StringUtils.isBlank(key)) {
                return false;
            }
            jedis = redisPool.getConnect(dbIndex);
            jedis.set(key,value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return true;
    }

    public void delete(int dbIndex,String key) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getConnect(dbIndex);
            jedis.del(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
    }

    public String get(int dbIndex,String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = redisPool.getConnect(dbIndex);
            value = jedis.get(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return value;
    }

    public Long hset(int dbIndex,String key, String fieid, String value) {
        Jedis jedis = null;
        Long s = null;
        try {
            jedis = redisPool.getConnect(dbIndex);
            s = jedis.hset(key, fieid, value);  
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return s;
    }
    
    public String hmset(int dbIndex,String key, Map<String, String> map) {
        Jedis jedis = null;
        String s = null;
        try {
            jedis = redisPool.getConnect(dbIndex);
            s = jedis.hmset(key, map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return s;
    }
    
    
    public String hget(int dbIndex,String key, String fieid) {  
        Jedis jedis = null;
        String s = null;
        try {
            jedis = redisPool.getConnect(dbIndex);
            s = jedis.hget(key, fieid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return s;
    }  
    
    
    
    public List<String> hmget(int dbIndex,String key,String... fieids) {
        Jedis jedis = null;
        List<String> list = new ArrayList<>();
        try {
            jedis = redisPool.getConnect(dbIndex);
            list = jedis.hmget(key, fieids);  
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return list;  
    }  
}
