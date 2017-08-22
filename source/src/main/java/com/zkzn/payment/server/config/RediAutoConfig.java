package com.zkzn.payment.server.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zkzn.payment.server.util.redis.RedisCommonCmd;
import com.zkzn.payment.server.util.redis.RedisPool;
import com.zkzn.payment.server.util.redis.RedisQueueCmd;
import com.zkzn.payment.server.util.redis.RedisQueueErrorHandler;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConfigurationProperties(prefix = "server.data.redis")
public class RediAutoConfig{

    private String host;
    
    private int port;
    
    private int timeout;
    
    private String password;
    
    private int poolMaxTotal;
    
    private int poolMaxIdle;
    
    private int poolMaxWait;
    
    private int queueIndex;
    
    public String getHost() {
        return host;
    }

    
    public int getPort() {
        return port;
    }

    
    public int getTimeout() {
        return timeout;
    }

    
    public String getPassword() {
        return password;
    }

    
    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    
    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    
    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    
    public int getQueueIndex() {
        return queueIndex;
    }

    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }


    @Bean
    public RedisQueueErrorHandler redisQueueHandler(){
        return new RedisQueueErrorHandler();
    }
    
    
    /**
     * @Title: 		 redisCommonCmd   
     * @Description: 用于redis客户端的基本操作
     * @param redisPool
     */
    @Bean
    public RedisCommonCmd redisCommonCmd(RedisPool redisPool){  
       RedisCommonCmd redisCommonCmd = new RedisCommonCmd();
       redisCommonCmd.setRedisPool(redisPool);
       return redisCommonCmd;
    }  
    
    /**
     * @Title: 		 redisQueueCmd   
     * @Description: 用于redis客户端的广播消息及队列机制的操作
     * @param redisPool
     * @param queueIndex
     */
    @Bean
    public RedisQueueCmd redisQueueCmd(RedisPool redisPool){  
        RedisQueueCmd redisQueueCmd = new RedisQueueCmd();
        redisQueueCmd.setQueueIndex(queueIndex);
        redisQueueCmd.setRedisPool(redisPool);
        return redisQueueCmd;
    }  
    
    /**
     * 
     * @Title: 		 redisPool   
     * @Description:  初始创建redisPool连接池
     * @param jedisPool
     */
    @Bean
    public RedisPool redisPool(JedisPool jedisPool){ 
        RedisPool redisPool = new RedisPool();
        redisPool.setJedisPool(jedisPool);
        return redisPool;
    }  
    
    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {  
        return new JedisPool(jedisPoolConfig, host, port, timeout);
    }  
      
    @Bean
    public JedisPoolConfig jedisPoolConfig () {  
        JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxTotal(poolMaxTotal);  
        config.setMaxIdle(poolMaxIdle);  
        config.setMaxWaitMillis(poolMaxWait);  
        return config;  
    }  
    
}
