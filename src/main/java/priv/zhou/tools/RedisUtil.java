package priv.zhou.tools;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类优化版
 *
 * @author zhou
 */
public class RedisUtil {

    @SuppressWarnings("unchecked")
    private final static RedisTemplate<String, Object> redisTemplate = AppContextUtil.getBean("redisTemplate", RedisTemplate.class);

    static {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
    }

    private RedisUtil() {
    }

    /**
     * 表达式获取key集合
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }


    /**
     * 删除key集合
     */
    public static Long delete(Set<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 保存数据
     */
    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }


    /**
     * 存数据有效时间为秒
     */
    public static void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 存数据有效时间
     */
    public static void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }


    /**
     * 获取数据
     */
    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 如果key为空则设置value
     */
    public static boolean setIfAbsent(String key, Object value) {
        return ParseUtil.unBox(redisTemplate.opsForValue().setIfAbsent(key, value));
    }

    /**
     * 设置value并返回前一个值
     */
    public static Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 获取key过期时间
     */
    public static Long expire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除key
     */
    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 以map状态保存数据
     */
    public static void putHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 以map状态保存数据(秒级别)
     */
    public static void putHash(String key, String hashKey, Object value, long timeout) {
        putHash(key, hashKey, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 以map状态保存数据
     */
    public static void putHash(String key, String hashKey, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取map
     */
    public static Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * 获取map
     */
    public static Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 判断name的Map总是否存在key
     */
    public static boolean hasHashKey(String key, Object hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 删除map中的键值对
     */
    public static void deleteHash(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 获取指定位置元素
     */
    public static Object getList(String key, Long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取集合长度
     */
    public static Long sizeList(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 获取集合长度
     */
    public static List<Object> rangeList(String key, Long start, Long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 添加元素
     */
    public static void addList(String key, Object... values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }


    /**
     * 在集合的指定位置插入元素,如果指定位置已有元素，则覆盖
     */
    public static void setList(String key, String value, Long index) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 截取集合元素长度，保留长度内的数据。
     */
    public static void trimList(String key, Long start, Long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 添加set
     */
    public static void addSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 是否存在于set
     */
    public static Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 是否存在于set
     */
    public static Boolean isMemberSet(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * Set中移除values
     */
    public static void removeSet(String key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }


    /**
     * ZSet中移除values
     */
    public static void removeZSet(String key, Object... values) {
        redisTemplate.opsForZSet().remove(key, values);
    }


    /**
     * 获取排行榜总数
     */
    public static Long sizeZSet(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }


    /**
     * 获取排行名次
     */
    public static Set<ZSetOperations.TypedTuple<Object>> rangeZSet(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 获取排行名次
     */
    public static Long rankZSet(String key, String value) {
        Long level = redisTemplate.opsForZSet().reverseRank(key, value);
        return null == level ? null : level + 1; // 索引排行
    }

    /**
     * 获取分数
     */
    public static Double scoreZSet(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }


    /**
     * 保存排行
     */
    public static void addZSet(String key, String value, Double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 增加分数
     */
    public static Double addScoreZSet(String key, String value, Double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }


    public static String generateId() {
        Date now = new Date();
        String prefix = DateUtil.format(now, "yyyyMMddHHmmss");
        RedisAtomicLong counter = new RedisAtomicLong(prefix, redisTemplate.getConnectionFactory());
        Long expire = counter.getExpire();
        if (expire == -1) {
            counter.expireAt(DateUtil.add(now, Calendar.SECOND, 20));
        }
        return String.format("%s%06d", prefix, counter.incrementAndGet());
    }

}