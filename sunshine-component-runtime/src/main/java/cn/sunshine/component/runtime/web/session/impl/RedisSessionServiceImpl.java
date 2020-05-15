package cn.sunshine.component.runtime.web.session.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import cn.sunshine.component.common.exception.SunshineException;
import cn.sunshine.component.common.util.JsonUtil;
import cn.sunshine.component.runtime.constant.RuntimeConstant;
import cn.sunshine.component.runtime.web.session.CommonSession;
import cn.sunshine.component.runtime.web.session.ISessionService;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月2日
 */
@Component
public class RedisSessionServiceImpl implements ISessionService{
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${common.session.timeout}")
    private Integer timeout;
	
	@Override
	public void set(CommonSession commonSession) throws SunshineException {
		this.set(commonSession.getSid(), commonSession, timeout, TimeUnit.SECONDS);
	}

	@Override
	public CommonSession get(String token) throws SunshineException {
		String key = getTokenKey(token);		
		return JsonUtil.fromJson((String)redisTemplate.boundValueOps(key).get(), CommonSession.class);
	}

	@Override
	public void remove(String token) throws SunshineException {
		redisTemplate.delete(getTokenKey(token));
	}

	@Override
	public void set(String token, Object session, long c, TimeUnit timeUnit) throws SunshineException {
		redisTemplate.opsForValue().set(getTokenKey(token), session,timeout, timeUnit);
	}

	@Override
	public <T> T get(String token, Class<T> clazz) throws SunshineException {
		String key = getTokenKey(token);
		return JsonUtil.fromJson((String)redisTemplate.boundValueOps(key).get(), clazz);
	}
	
	private String getTokenKey(String token) {
		return RuntimeConstant.SESSION_TOKEN_PREFIX +  token;
	}	


}
