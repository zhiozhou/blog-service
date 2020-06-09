package priv.zhou.tools;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis分布式锁优化重构
 *
 * @author zhou
 * @since 2019.12.13
 */
@Slf4j
public class RedisLock implements DistributedLock {

    /**
     * 锁住的key
     */
    private String key;

    /**
     * 锁过期时间,防止死锁
     * 默认10s
     */
    private int expireMs;

    /**
     * 锁等待时间,防止无限等待
     * 默认10s
     */
    private int waitMs;

    /**
     * 上锁标识
     */
    private boolean locked;


    /**
     * 构建分布式锁
     *
     * @param key 锁住的key
     */
    public static RedisLock build(String key) {
        return build(key, 10, 10);
    }

    /**
     * 构建分布式锁
     *
     * @param key  锁住的key
     * @param wait 等待秒数
     */
    public static RedisLock build(String key, int wait) {
        return build(key, wait, 10);
    }

    /**
     * 构建Redis分布式锁
     * key自动区分环境
     *
     * @param key    锁住的key
     * @param wait   等待秒数
     * @param expire 过期秒数
     */
    public static RedisLock build(String key, int wait, int expire) {
        RedisLock lock = new RedisLock();
        lock.key = String.format("lock_%s_%s", AppContextUtil.getProfiles(), key);
        lock.waitMs = wait * 1000;
        lock.expireMs = expire * 1000;
        lock.locked = false;
        return lock;
    }

    private RedisLock() {
    }

    /**
     * 获取所
     */
    public synchronized boolean acquire() {
        try {
            long loopMs = 64;
            int waitCount = waitMs;
            while (waitCount > -1) {
                String expireStr = String.valueOf(System.currentTimeMillis() + expireMs + 1);
                if (RedisUtil.setIfAbsent(key, expireStr)) {
                    return locked = true;
                }
                String value = (String) RedisUtil.get(key);
                if (null != value && Long.parseLong(value) < System.currentTimeMillis() && value.equals(RedisUtil.getAndSet(key, expireStr))) {
                    return locked = true;
                }
                waitCount -= loopMs;
                Thread.sleep(loopMs);
            }
        } catch (Exception e) {
            log.error("获取锁异常 -->", e);
        }
        return false;
    }

    /**
     * 释放锁
     */
    public synchronized void close() {
        if (locked) {
            RedisUtil.delete(key);
            locked = false;
        }
    }
}
