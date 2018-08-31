package com.cloudhelios.atlantis.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * author: chenwei
 * createDate: 18-8-30 下午3:44
 * description:
 */

public class RedisUtil {
    private RedisTemplate<String, Object> redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    public void set(String k, Object v, long time) {
        if (v instanceof String && stringRedisTemplate != null) {
            stringRedisTemplate.opsForValue().set(k, (String) v);
        } else {
            redisTemplate.opsForValue().set(k, v);
        }
        if (time > 0) redisTemplate.expire(k, time, TimeUnit.SECONDS);
    }

    public void set(String k, Object v) {
        set(k, v, -1);
    }

    public boolean contains(String key) {
        return redisTemplate.hasKey(key);
    }

    public String get(String k) {
        if (stringRedisTemplate != null) {
            return stringRedisTemplate.opsForValue().get(k);
        } else {
            return (String) redisTemplate.opsForValue().get(k);
        }
    }

    public <T> T getObject(String k) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return (T) valueOps.get(k);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public void expire(String key, long timeOut, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeOut, timeUnit);
    }

    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Double increment(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }
}
