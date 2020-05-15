package cn.sunshine.component.redis.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import cn.sunshine.component.redis.service.IRedisService;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年3月19日
 */
public class RedisServiceImpl implements IRedisService {

	
	private RedisTemplate<String, Object> redisTemplate;

	public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void set(String key, Object value, long time) {
		redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
	}

	@Override
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public Boolean del(String key) {
		return redisTemplate.delete(key);
	}

	@Override
	public Long del(List<String> keys) {
		return redisTemplate.delete(keys);
	}

	@Override
	public Boolean expire(String key, long time) {
		return redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	@Override
	public Long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	@Override
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public Long incr(String key, long delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	@Override
	public Long decr(String key, long delta) {
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	@Override
	public Object hGet(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	@Override
	public Boolean hSet(String key, String hashKey, Object value, long time) {
		redisTemplate.opsForHash().put(key, hashKey, value);
		return expire(key, time);
	}

	@Override
	public void hSet(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	@Override
	public Map<Object, Object> hGetAll(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	@Override
	public Boolean hSetAll(String key, Map<String, Object> map, long time) {
		redisTemplate.opsForHash().putAll(key, map);
		return expire(key, time);
	}

	@Override
	public void hSetAll(String key, Map<String, Object> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}

	@Override
	public void hDel(String key, Object... hashKey) {
		redisTemplate.opsForHash().delete(key, hashKey);
	}

	@Override
	public Boolean hHasKey(String key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}

	@Override
	public Long hIncr(String key, String hashKey, Long delta) {
		return redisTemplate.opsForHash().increment(key, hashKey, delta);
	}

	@Override
	public Long hDecr(String key, String hashKey, Long delta) {
		return redisTemplate.opsForHash().increment(key, hashKey, -delta);
	}

	@Override
	public Set<Object> sMembers(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	@Override
	public Long sAdd(String key, Object... values) {
		return redisTemplate.opsForSet().add(key, values);
	}

	@Override
	public Long sAdd(String key, long time, Object... values) {
		Long count = redisTemplate.opsForSet().add(key, values);
		expire(key, time);
		return count;
	}

	@Override
	public Boolean sIsMember(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}

	@Override
	public Long sSize(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	@Override
	public Long sRemove(String key, Object... values) {
		return redisTemplate.opsForSet().remove(key, values);
	}

	@Override
	public List<Object> lRange(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	@Override
	public Long lSize(String key) {
		return redisTemplate.opsForList().size(key);
	}

	@Override
	public Object lIndex(String key, long index) {
		return redisTemplate.opsForList().index(key, index);
	}

	@Override
	public Long lPush(String key, Object value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}

	@Override
	public Long lPush(String key, Object value, long time) {
		Long index = redisTemplate.opsForList().rightPush(key, value);
		expire(key, time);
		return index;
	}

	@Override
	public Long lPushAll(String key, Object... values) {
		return redisTemplate.opsForList().rightPushAll(key, values);
	}

	@Override
	public Long lPushAll(String key, Long time, Object... values) {
		Long count = redisTemplate.opsForList().rightPushAll(key, values);
		expire(key, time);
		return count;
	}

	@Override
	public Long lRemove(String key, long count, Object value) {
		return redisTemplate.opsForList().remove(key, count, value);
	}
	
	@Override
	public Object lRPop(String key, long timeout) {
		return redisTemplate.opsForList().rightPop(key, timeout, TimeUnit.SECONDS);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean lock(String lockKey, long expireTime) {
//		String value = "lock";
//		String script = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then return redis.call('pexpire', KEYS[1], ARGV[2]) else return 0 end";
//		DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>(script);
//		Long result = redisTemplate.execute(defaultRedisScript, Collections.singletonList(lockKey), value, expireTime);
//		return Objects.equals(result, 1L) ? true : false;

		try {
			String value = "lock";
			return  redisTemplate.execute((RedisCallback<Boolean>) connection -> {
				Object nativeConnection = connection.getNativeConnection();
				String redisResult = "";
				RedisSerializer<String> stringRedisSerializer = (RedisSerializer<String>) redisTemplate
						.getKeySerializer();
				// lettuce连接包下序列化键值，否知无法用默认的ByteArrayCodec解析
				byte[] keyByte = stringRedisSerializer.serialize(lockKey);
				byte[] valueByte = stringRedisSerializer.serialize(value);
				// lettuce连接包下 redis 单机模式setnx
				if (nativeConnection instanceof RedisAsyncCommands) {
					RedisAsyncCommands commands = (RedisAsyncCommands) nativeConnection;
					// 同步方法执行、setnx禁止异步
					redisResult = commands.getStatefulConnection().sync().set(keyByte, valueByte,
							SetArgs.Builder.nx().ex(expireTime));
				}
				// lettuce连接包下 redis 集群模式setnx
				if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
					RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
					redisResult = clusterAsyncCommands.getStatefulConnection().sync().set(keyByte, keyByte,
							SetArgs.Builder.nx().ex(expireTime));
				}
				// 返回加锁结果
				return "OK".equalsIgnoreCase(redisResult);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
