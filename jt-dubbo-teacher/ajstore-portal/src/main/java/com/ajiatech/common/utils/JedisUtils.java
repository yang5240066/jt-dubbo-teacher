package com.ajiatech.common.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

@Component
public class JedisUtils {

	@Autowired
	private JedisPool jedisPool;
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	
	public static JedisUtils getJedisUtils() {
		return jedisUtils;
	}

	public static void setJedisUtils(JedisUtils jedisUtils) {
		JedisUtils.jedisUtils = jedisUtils;
	}

	private static JedisUtils jedisUtils;
	
	@PostConstruct
	public void init() {    
		jedisUtils = this;
	}
	
	public JedisUtils() {
		super();
	}

	public JedisPool getPool() {
		return jedisPool;
	}

	/**
	 * 从jedis连接池中获取获取jedis对象
	 * 
	 * @return
	 */
	public synchronized Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 获取JedisUtil实例
	 * 
	 * @return
	 */
	public static JedisUtils getInstance() {
		return jedisUtils;
	}

	/**
	 * 回收jedis
	 * 
	 * @param jedis
	 */
	public synchronized void returnJedis(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 设置过期时间
	 * 
	 * @author ruan 2013-4-11
	 * @param key
	 * @param seconds
	 */
	public void expire(String key, int seconds) {
		if (seconds <= 0) {
			return;
		}
		Jedis jedis = getJedis();
		jedis.expire(key, seconds);
		returnJedis(jedis);
	}

	/**
	 * 设置默认过期时间
	 * 
	 * @author ruan 2013-4-11
	 * @param key
	 */
	public void expire(String key,Long expire) {
		expire(key, expire);
	}

	// *******************************************Keys*******************************************//
	public class Keys {

		/**
		 * 清空所有key
		 */
		public String flushAll() {
			Jedis jedis = getJedis();
			String stata = jedis.flushAll();
			returnJedis(jedis);
			return stata;
		}

		/**
		 * 更改key
		 * 
		 * @param String
		 *            oldkey
		 * @param String
		 *            newkey
		 * @return 状态码
		 */
		public String rename(String oldkey, String newkey) {
			Jedis jedis = getJedis();
			return jedis.rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
		}
	}
}
