package com.redis.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.b2c.rest.jedis.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {
    /**
     * single connect
     */
    @Test
    public void insert(){
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("name", "bar");
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }
    
    @Test
    public void poolInsert(){
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
        String name = jedis.get("name");
        System.out.println(name);
    }
    
    @Test
    public void testJedisCluster(){
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数
        config.setMaxTotal(30);
        // 最大连接空闲数
        config.setMaxIdle(2);

        //集群结点
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("192.168.222.115", 7001));
        jedisClusterNode.add(new HostAndPort("192.168.222.115", 7002));
        jedisClusterNode.add(new HostAndPort("192.168.222.115", 7003));
        jedisClusterNode.add(new HostAndPort("192.168.222.115", 7004));
        jedisClusterNode.add(new HostAndPort("192.168.222.115", 7005));
        jedisClusterNode.add(new HostAndPort("192.168.222.115", 7006));
        JedisCluster jc = new JedisCluster(jedisClusterNode, config);
       
        jc.set("name", "zhangsan");
        String value = jc.get("name");
        System.out.println(value);
    }
    
    @Test
    public void springRedis(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisClient client = (JedisClient) applicationContext.getBean("jedisClient");
        String name = client.get("name");
        System.out.println(name);
        
    }
}