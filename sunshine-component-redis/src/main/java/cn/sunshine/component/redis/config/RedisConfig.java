package cn.sunshine.component.redis.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.sunshine.component.redis.service.impl.RedisServiceImpl;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p>
 * Redis配置类
 * </p>
 *
 * @author wuj
 * @since 2020年3月19日
 */

@Configuration
@ConditionalOnClass(CacheConfig.class)
@EnableCaching
public class RedisConfig {

	/**
	 * redis数据库自定义key
	 */
	public static final String REDIS_KEY_DATABASE = "sunshine";

	@Bean
	public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPool,
			RedisClusterConfiguration jedisConfig) {
		JedisConnectionFactory factory = new JedisConnectionFactory(jedisConfig, jedisPool);
		factory.afterPropertiesSet();
		return factory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisSerializer<Object> serializer = redisSerializer();
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(serializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	public RedisSerializer<Object> redisSerializer() {
		// 创建JSON序列化器
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(objectMapper);
		return serializer;
	}

	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
		// 设置Redis缓存有效期为1天
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()))
				.entryTtl(Duration.ofDays(1));
		return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
	}

	@Bean
	public RedisServiceImpl bussRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		return new RedisServiceImpl(redisTemplate);
	}

	@Configuration
	public static class JedisConf {
		@Value("${spring.redis.cluster.nodes:127.0.0.1:7000,127.0.0.1:7001,127.0.0.1:7002}")
		private String nodes;
		@Value("${spring.redis.cluster.max-redirects:3}")
		private Integer maxRedirects;
		@Value("${spring.redis.password:}")
		private String password;
		@Value("${spring.redis.database:0}")
		private Integer database;

		@Value("${spring.redis.jedis.pool.max-active:8}")
		private Integer maxActive;
		@Value("${spring.redis.jedis.pool.max-idle:8}")
		private Integer maxIdle;
		@Value("${spring.redis.jedis.pool.max-wait:-1}")
		private Long maxWait;
		@Value("${spring.redis.jedis.pool.min-idle:0}")
		private Integer minIdle;

		@Bean
		public JedisPoolConfig jedisPool() {
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxIdle(maxIdle);
			jedisPoolConfig.setMaxWaitMillis(maxWait);
			jedisPoolConfig.setMaxTotal(maxActive);
			jedisPoolConfig.setMinIdle(minIdle);
			return jedisPoolConfig;
		}

		@Bean
		public RedisClusterConfiguration jedisConfig() {
			RedisClusterConfiguration config = new RedisClusterConfiguration();

			String[] sub = nodes.split(",");
			List<RedisNode> nodeList = new ArrayList<>(sub.length);
			String[] tmp;
			for (String s : sub) {
				tmp = s.split(":");
				// fixme 先不考虑异常配置的case
				nodeList.add(new RedisNode(tmp[0], Integer.valueOf(tmp[1])));
			}

			config.setClusterNodes(nodeList);
			config.setMaxRedirects(maxRedirects);
			config.setPassword(RedisPassword.of(password));
			return config;
		}
	}

//	@Bean
//	public KeyGenerator keyGenerator() {
//		return new KeyGenerator() {
//			/** 重写生成key方法 */
//			public Object generate(Object o, Method method, Object... objects) {
//				StringBuilder sb = new StringBuilder(prefix);
//				CacheConfig cacheConfig = o.getClass().getAnnotation(CacheConfig.class);
//				Cacheable cacheable = method.getAnnotation(Cacheable.class);
//				CachePut cachePut = method.getAnnotation(CachePut.class);
//				CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
//				if (cacheable != null) {
//					String[] cacheNames = cacheable.value();
//					if (isNotEmpty(cacheNames)) {
//						sb.append(cacheNames[0]);
//					}
//				} else if (cachePut != null) {
//					String[] cacheNames = cachePut.value();
//					if (isNotEmpty(cacheNames)) {
//						sb.append(cacheNames[0]);
//					}
//				} else if (cacheEvict != null) {
//					String[] cacheNames = cacheEvict.value();
//					if (isNotEmpty(cacheNames)) {
//						sb.append(cacheNames[0]);
//					}
//				}
//				if (cacheConfig != null && sb.toString().equals(prefix)) {
//					String[] cacheNames = cacheConfig.cacheNames();
//					if (isNotEmpty(cacheNames)) {
//						sb.append(cacheNames[0]);
//					}
//				}
//				if (sb.toString().equals(prefix)) {
//					sb.append(o.getClass().getName()).append(".").append(method.getName());
//				}
//				sb.append(":");
//				if (objects != null) {
//					for (Object object : objects) {
//						sb.append(JSON.toJSONString(object));
//					}
//				}
//				return sb.toString();
//			}
//		};
//	}
	@SuppressWarnings("unchecked")
	private <T> boolean isNotEmpty(final T... array) {
		return (array != null && array.length != 0);
	}
}
