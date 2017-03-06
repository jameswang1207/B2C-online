package com.b2c.rest.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.b2c.pojo.B2cResult;
import com.b2c.rest.jedis.JedisClient;
import com.b2c.rest.service.JedisService;
import com.b2c.util.ExceptionUtil;

@Service
public class JedisServiceImp implements JedisService{
    @Autowired
    private JedisClient jedisClient;
    
    @Value("${INDEX_CONTENT_CACHE_KEY}")
    private String INDEX_CONTENT_CACHE_KEY;

    @Override
    public B2cResult syncContent(long key) {
        try {
            long result = jedisClient.hdel(INDEX_CONTENT_CACHE_KEY, key + "");
            return B2cResult.ok(result);
        } catch (Exception e) {
            return B2cResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
