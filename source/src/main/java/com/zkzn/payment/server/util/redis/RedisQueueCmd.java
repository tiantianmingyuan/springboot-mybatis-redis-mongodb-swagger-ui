package com.zkzn.payment.server.util.redis;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zkzn.payment.server.util.SerializeUtil;

import redis.clients.jedis.Jedis;

@Component
public class RedisQueueCmd {

    @Autowired
    private RedisPool redisPool;
    
    private Integer queueIndex;  

    public void setQueueIndex(Integer queueIndex) {
        this.queueIndex = queueIndex;
    }
    
    public void setRedisPool(RedisPool redisPool) {
        this.redisPool = redisPool;
    }
    
    /**
     * 存储REDIS队列 消息发布
     * @param String channel topic
     * @param String message 消息
     * SerializeUtil.serialize2 序列化字符串信息
     */
    public void publish(String channel, String message) {
 
        Jedis jedis = null;
        try {
 
            jedis = redisPool.getConnect(queueIndex);
            jedis.publish(channel, SerializeUtil.serialize2(message));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
 
        }
    }
    
    /**
     * 存储REDIS队列 顺序存储
     * @param byte[] key reids键名
     * @param byte[] value 键值
     */
    public void lpush(String key, String value) {
 
        Jedis jedis = null;
        try {
 
            jedis = redisPool.getConnect(queueIndex);
            jedis.lpush(key, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
 
        }
    }
 
    /**
     * 存储REDIS队列 反向存储
     * @param byte[] key reids键名
     * @param byte[] value 键值
     */
    public void rpush(byte[] key, byte[] value) {
 
        Jedis jedis = null;
        try {
 
            jedis = redisPool.getConnect(queueIndex);
            jedis.rpush(key, value);
 
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
    }
 
    /**
     * 存储REDIS队列 反向存储
     * @param String key reids键名
     * @param String value 键值
     */
    public void rpush(String key, String value) {
 
        Jedis jedis = null;
        try {
 
            jedis = redisPool.getConnect(queueIndex);
            jedis.rpush(key, value);
 
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
    }
    
    
    /**
     * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端
     * @param byte[] key reids键名
     * @param byte[] value 键值
     */
    public void rpoplpush(byte[] key, byte[] destination) {
 
        Jedis jedis = null;
        try {
 
            jedis = redisPool.getConnect(queueIndex);
            jedis.rpoplpush(key, destination);
 
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
    }
 
    /**
     * 获取队列数据
     * @param byte[] key 键名
     * @return
     */
    public  List<byte[]> lpopList(byte[] key) {
 
        List<byte[]> list = null;
        Jedis jedis = null;
        try {
 
            jedis = redisPool.getConnect(queueIndex);
            list = jedis.lrange(key, 0, -1);
 
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return list;
    }
 
    /**
     * 获取队列数据
     * @param byte[] key 键名
     * @return
     */
    public byte[] rpop(byte[] key) {
 
        byte[] bytes = null;
        Jedis jedis = null;
        try {
 
            jedis = redisPool.getConnect(queueIndex);
            bytes = jedis.rpop(key);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return bytes;
    }
    
    /**
     * 监听获取队列数据 brpop
     * @param string key 键名    int timeout 阻塞时间
     * @return 当有元素弹出时会返回一个双元素的多批量值，其中第一个元素是弹出元素的 key，第二个元素是 value。
     */
    public List<String> brpop(int timeout, String key) {
 
        List<String> value = null;
        Jedis jedis = null;
        try {
            jedis = redisPool.getConnect(queueIndex);
            value = jedis.brpop(timeout, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisPool.release(jedis);
        }
        return value;
    }
    
}
