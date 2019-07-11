package com.first.test.demo.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chris
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig  {

    @Autowired
    LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    public RedisTemplate<Object, Object> redisTemplate1() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        //LettuceConnectionFactory 连接客户端，如果使用的是jedis，则需要更改为JedisConnectionFactory Lettuce相当于一个客户端，底层使用的是netty
        template.setConnectionFactory(lettuceConnectionFactory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        //使用StringRedisSerializer来序列化和反序列化redis的hashKey，value 值
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }

    /**
     * 对hash类型的数据操作
     *
     * @param stringRedisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, String> hashOperations(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForHash();
    }


    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(500);
        poolConfig.setMinIdle(0);
        poolConfig.setMaxTotal(500);
        poolConfig.setMaxWaitMillis(1000);
        poolConfig.setTestOnBorrow(true);

        //这里单独配置超时时间，连接池管理
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(200))
                .shutdownTimeout(Duration.ofMillis(200))
                .poolConfig(poolConfig)
                .build();

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory
                (redisStandaloneConfiguration, lettuceClientConfiguration);
        lettuceConnectionFactory.setValidateConnection(true);
        return lettuceConnectionFactory;
    }

    @Bean
    public CacheManager cacheManager(){
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues().entryTtl(Duration.ofSeconds(60));

//          方式一 无法自定义每个key的过期时间
//        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
//                .RedisCacheManagerBuilder
//                .fromConnectionFactory(lettuceConnectionFactory);
//        return builder.build();

       return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory),
                cacheConfiguration,
                getRedisCacheConfigurationMap());

    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("Ticker",redisCacheConfiguration(30));
        return redisCacheConfigurationMap;
    }


    // 根据time seconds设置key的过期时间
    private RedisCacheConfiguration redisCacheConfiguration(int time){
        return RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues().entryTtl(Duration.ofSeconds(time));
    }


//    注解式keyName的生成方式
//    @Bean
//    public KeyGenerator keyGenerator(){
//        /// TODO: 2019/7/11
//        return null;
//    }

}
