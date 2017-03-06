package com.b2c.rest.service;

import com.b2c.pojo.B2cResult;

public interface JedisService {

    public B2cResult syncContent(long key);
}
