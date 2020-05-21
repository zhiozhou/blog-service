package priv.zhou.tools;

/**
 * 分布式锁
 */
public interface DistributedLock extends AutoCloseable {


	/**
	 * 获取锁
	 *
	 * @return 成功 true 失败false
	 */
	boolean acquire();


	/**
	 * 释放锁
	 */
	void close();

}
