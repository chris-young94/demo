package com.first.test.demo.demo4.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.first.test.demo.demo4.util.ArithmeticUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @author oxchains
 * @time 2018-11-12 9:51
 * @name RedisCacheService
 * @desc:
 */
@Slf4j
@Service
public class RedisCacheService {

    public static final String DATA = "data";
    public static final String EXPIRE = "expire";

    public static final String LOCK_SUFFIX = "_lk";

    private static final String STR_NULL = "null";

    public static final int ExpandExpireTime = 10;

    //加锁默认超时时间
    private static final long DEFAULT_TIMEOUT_SECOND = 5;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 缓存失效, 或者不存在, 会去竞争一把锁刷新缓存
     * 缓存中存储了一个失效时间, 比redis实际的失效时间短10s, 防止redis突然失效, 大家都等待一个人去刷新缓存导致的等待
     *
     * @param key
     * @param clazz
     * @param expire
     * @param loadback
     * @param <T>
     * @return
     */
    public <T> T findCache(String key, TypeReference<T> clazz, long expire, CacheLoadback<T> loadback) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String jsonStr = valueOperations.get(key);

        //redis挂了, 或程序启动时候增加缓存
        if (StringUtils.isBlank(jsonStr)) {
            T result = loadback.loadback();
            boolean acquireLock = redisTemplate.opsForValue().setIfAbsent(key + LOCK_SUFFIX, DEFAULT_TIMEOUT_SECOND);
            if (acquireLock) {
                doRefresh(key, expire, JSON.toJSONString(result));
            }
            return result;
        }

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String expireTime = jsonObject.getString(EXPIRE);
        long now = System.currentTimeMillis() / 1000;
        log.debug("load:{}, now:{}, ex:{}", key, now, expireTime);
        if (now >= Long.parseLong(expireTime)) {
            boolean acquireLock = redisTemplate.opsForValue().setIfAbsent(key + LOCK_SUFFIX, DEFAULT_TIMEOUT_SECOND);
            if (acquireLock) {
                T result = loadback.loadback();
                doRefresh(key, expire, JSON.toJSONString(result));
            }
        }
        return parseObject(jsonObject.getString(DATA), clazz);
    }

    /**
     * 刷新缓存
     *
     * @param key
     * @param expire
     * @param result
     */
    public void doRefresh(String key, long expire, String result) {

        log.info("Redis expire.. refresh:{}", key);
        setRedis(key, expire, result);
        redisTemplate.delete(key + LOCK_SUFFIX);
    }

    public void setRedis(String key, long expire, String result) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if (StringUtils.isBlank(result) || STR_NULL.equalsIgnoreCase(result.trim())) {
            log.warn("refresh redis data null");
            return;
        }

        long now = System.currentTimeMillis() / 1000;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(DATA, result);
        jsonObject.put(EXPIRE, expire + now);

        long realExpireTime = ExpandExpireTime + expire;

        valueOperations.set(key, jsonObject.toJSONString(), realExpireTime, TimeUnit.SECONDS);
    }

    public void removeRedis(String key) {
        redisTemplate.delete(key);
    }


    public <T> T findHashCache(String hashKey, String key, TypeReference<T> clazz, long expire, CacheLoadback<T> loadback) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String jsonStr = hashOperations.get(hashKey, key);
        if (StringUtils.isNotBlank(jsonStr)) {
            log.info("Read data from redis hash cache: {}", jsonStr);
            return JSON.parseObject(jsonStr, clazz);
        } else {
            synchronized (this) {
                jsonStr = hashOperations.get(hashKey, key);
                if (StringUtils.isNotBlank(jsonStr)) {
                    log.info("Read data from redis hash cache: {}", jsonStr);
                }
                T result = loadback.loadback();
                if (null != result) {
                    hashOperations.put(hashKey, key, JSON.toJSONString(result));
                    redisTemplate.expire(hashKey, expire, TimeUnit.SECONDS);
                }
                return result;
            }
        }
    }


    public <T> T findShardingHashCache(String hashKey, String key, TypeReference<T> clazz, long expire, CacheLoadback<T> loadback) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        Long value = ArithmeticUtils.getSingleDigits(key);
        String jsonStr = hashOperations.get(hashKey + value, key);
        if (StringUtils.isNotBlank(jsonStr)) {
            log.info("Read data from redis hash cache: {}", jsonStr);
            return JSON.parseObject(jsonStr, clazz);
        } else {
            synchronized (this) {
                jsonStr = hashOperations.get(hashKey + value, key);
                if (StringUtils.isNotBlank(jsonStr)) {
                    log.info("Read data from redis hash cache: {}", jsonStr);
                }
                T result = loadback.loadback();
                if (null != result) {
                    Map<String, String> map = hashOperations.entries(hashKey + value);
                    if (null == map || map.isEmpty()) {
                        hashOperations.put(hashKey + value, key, JSON.toJSONString(result));
                        redisTemplate.expire(hashKey + value, expire, TimeUnit.SECONDS);
                    } else {
                        hashOperations.put(hashKey + value, key, JSON.toJSONString(result));
                    }
                }
                return result;
            }
        }
    }
}

