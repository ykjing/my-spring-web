package com.ticketcenter.user.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableCaching
public class RedisStandaloneConfiguration {

	  @Bean
	  JedisConnectionFactory redisConnectionFactory() {
	    return new JedisConnectionFactory();
	  }
	  
	  @Bean
	  StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

	    StringRedisTemplate template = new StringRedisTemplate();
	    template.setConnectionFactory(redisConnectionFactory);
	    return template;
	  }
}
