package com.zt10.proxyPool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    ValueOperations<String, String> stringRedis;

    @PostConstruct
    public void init(){
        stringRedis=redisTemplate.opsForValue();
    }

    //@Autowired(required = false)
    //public void setRedisTemplate(RedisTemplate redisTemplate) {
    //    RedisSerializer stringSerializer = new StringRedisSerializer();
    //    redisTemplate.setKeySerializer(stringSerializer);
    //    redisTemplate.setValueSerializer(stringSerializer);
    //    redisTemplate.setHashKeySerializer(stringSerializer);
    //    redisTemplate.setHashValueSerializer(stringSerializer);
    //    this.redisTemplate = redisTemplate;
    //}

    @Test
    public void testString (){
        stringRedis.set("aaa", "丁洁");
        System.out.println(stringRedis.get("name"));
    }


}
