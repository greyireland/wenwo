package com.finalx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.finalx.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by tengyu on 2016/8/3.
 */
@Component
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool;
    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }

    public long sadd(String key, String value) {
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("error:" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value) {
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("error:" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Jedis jedis=new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

        //基本
        jedis.set("hello", "world");
        print(1,jedis.get("hello"));
        jedis.rename("hello", "newhello");
        print(1, jedis.get("newhello"));
        jedis.setex("hello", 15, "world");

        //++增加和减少
        jedis.set("pv", "100");
        jedis.incr("pv");
        jedis.incrBy("pv", 10);
        jedis.decrBy("pv", 2);
        print(2, jedis.get("pv"));

        print(3, jedis.keys("*"));

        String listName = "list";
        for (int i = 0; i < 10; i++) {
            jedis.lpush(listName, "a" + String.valueOf(i));
        }
        print(4, jedis.lrange(listName, 0, 100));
        print(5, jedis.llen(listName));
        print(6, jedis.lpop(listName));
        print(7, jedis.llen(listName));
        print(8, jedis.lindex(listName, 3));
        print(9, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "xx"));
        print(10, jedis.lrange(listName, 0, 12));

        //hash
        String userKey = "userxx";
        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "12");
        jedis.hset(userKey, "phone", "110119");
        print(12, jedis.hget(userKey, "name"));
        jedis.hdel(userKey, "phone");
        print(13, jedis.hgetAll(userKey));
        print(14, jedis.hexists(userKey, "age"));
        //主key,和副key
        print(15, jedis.hkeys(userKey));
        print(16, jedis.hvals(userKey));
        jedis.hsetnx(userKey, "name", "tom");
        jedis.hsetnx(userKey, "school", "csust");
        print(17, jedis.hgetAll(userKey));

        //set
        String likeKey1 = "commentLike1";
        String likeKey2 = "commentLike2";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i * i));
        }

        print(jedis.smembers(likeKey1));
        print((jedis.smembers(likeKey2)));
        print(jedis.sunion(likeKey1, likeKey2));
        print(jedis.sdiff(likeKey1, likeKey2));
        print(jedis.sinter(likeKey1, likeKey2));
        print(jedis.sismember(likeKey1, "12"));
        print(jedis.scard(likeKey1));
        jedis.srem(likeKey1, "4");
        jedis.smove(likeKey1, likeKey2, "5");
        print(jedis.smembers(likeKey1));
        print(jedis.smembers(likeKey2));

        //sorted set
        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "jim");
        jedis.zadd(rankKey, 60, "Ben");
        jedis.zadd(rankKey, 90, "Lee");
        jedis.zadd(rankKey, 75, "Lucy");
        jedis.zadd(rankKey, 80, "Mei");
        print(jedis.zcard(rankKey));
        print(jedis.zcount(rankKey, 65, 100));
        print(jedis.zscore(rankKey, "jim"));
        jedis.zincrby(rankKey, 2, "jim");
        print(jedis.zscore(rankKey,"jim"));
        print(jedis.zrange(rankKey, 0, 100));
        print(jedis.zrange(rankKey, 1, 3));
        print(jedis.zrevrange(rankKey, 1, 3));
        print(jedis.zrank(rankKey, "Ben"));
        print(jedis.zrevrank(rankKey, "Ben"));

        //user
        User user = new User();
        user.setName("xx");
        user.setPassword("ppp");
        user.setHeadUrl("a.png");
        user.setSalt("salt");
        user.setId(1);
        print(46, JSONObject.toJSONString(user));
        jedis.set("user1", JSONObject.toJSONString(user));

        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value, User.class);
        print(47, user2);
    }
    public static void print(int index, Object object) {
        System.out.println(String.format("%d,%s", index, object.toString()));
    }
    public static int index=0;
    public static void print(Object object) {
        System.out.println(String.format("%d,%s", ++index, object.toString()));
    }


    public Jedis getJedis() {
        return pool.getResource();
    }

    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            logger.error("error :"+e.getMessage());
        }finally{
            //todo
        }
        return null;
    }

    public List<Object> exec(Transaction tx, Jedis jedis) {
        try {
            return tx.exec();
        } catch (Exception e) {
            logger.error("error :" + e.getMessage());
        }finally{
            if (tx != null) {
                try{
                    tx.close();
                }catch(IOException ioe){
                    //// TODO: 2016/8/19
                }
            }
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrange(String key, int start, int end) {
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.zrange(key, start, end);
        }catch (Exception e) {
            logger.error("error :" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
    //倒排序
    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public Double zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


}
