package cn.sunshine.component.runtime.web.session;

import java.util.concurrent.TimeUnit;

import cn.sunshine.component.common.exception.SunshineException;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月1日
 */
public interface ISessionService {

	void set(CommonSession commonSession) throws SunshineException;

    CommonSession get(String token) throws SunshineException;

    void remove(String token) throws SunshineException;

    void set(String token, Object session, long c , TimeUnit timeUnit) throws SunshineException;

    <T> T get(String token, Class<T> clazz) throws SunshineException;
}
