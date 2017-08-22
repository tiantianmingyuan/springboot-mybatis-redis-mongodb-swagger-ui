package com.zkzn.payment.server.util.redis;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisQueueErrorHandler {

    private static final String RedisErrorMessage = "RedisErrorMessage";
    
    private AtomicInteger cycleTime = new AtomicInteger(0);
    
    private static final int dequeueCount = 5;
    
    private static final int dbIndex = 3;
    
    private static final int expireTime = 43200000;
    
    @Autowired
    private RedisCommonCmd redisCommonCmd;
    
    @Autowired
    private RedisQueueCmd redisQueueCmd;
    
    
    
    public void handlerErrorMessage(String key, String value, Exception exception) {
        if (cycleTime.get() >= dequeueCount) {
            redisCommonCmd.set(dbIndex, expireTime, RedisErrorMessage+":"+key+":"+UUID.randomUUID(), value);
            //接下来 可以处理exception记录日志 等动作
        }else {
            redisQueueCmd.rpush(key, value);
            cycleTime.getAndIncrement();
        }
    }
    
   
}
