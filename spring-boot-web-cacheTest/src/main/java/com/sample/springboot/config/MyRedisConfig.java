package com.sample.springboot.config;

import com.sample.springboot.entities.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class MyRedisConfig {

  @Bean
  public RedisTemplate<Object, Employee> empRedisTemplate(
    RedisConnectionFactory redisConnectionFactory)
    throws UnknownHostException {
    RedisTemplate<Object, Employee> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    Jackson2JsonRedisSerializer<Employee> ser = new Jackson2JsonRedisSerializer<>(Employee.class);
    template.setDefaultSerializer(ser);
    return template;
  }

}
