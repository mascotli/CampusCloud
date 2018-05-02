package com.mascot.campuscloud.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
	
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager rcm = RedisCacheManager.create(jedisConnectionFactory());
        //设置缓存过期时间
        //rcm.setDefaultExpiration(60);//秒
        return rcm;
    }

	/**
	 * jedis
	 */
	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration standloneConfig = new RedisStandaloneConfiguration("godfather.mascot.com.cn",
				6379);
		// RedisSentinelConfiguration sentinelConfig = new
		// RedisSentinelConfiguration().master("mymaster")
		// .sentinel("godfather.mascot.com.cn",
		// 26379).sentinel("godfather.mascot.com.cn", 26380);
		return new JedisConnectionFactory(standloneConfig);
	}

	/**
	 * Lettuce
	 */
	// @Bean
	public RedisConnectionFactory lettuceConnectionFactory() {
		RedisStandaloneConfiguration standloneConfig = new RedisStandaloneConfiguration("godfather.mascot.com.cn",
				6379);
		// RedisSentinelConfiguration sentinelConfig = new
		// RedisSentinelConfiguration().master("mymaster")
		// .sentinel("godfather.mascot.com.cn", 26379);
		return new LettuceConnectionFactory(standloneConfig);
	}

	@Bean(name = "redistoken")
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

}