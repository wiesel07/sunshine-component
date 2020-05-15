package cn.sunshine.component.runtime.web.session;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月2日
 */
public class TokenManager {

	private static ThreadLocal<CommonSession> sessionPools = new ThreadLocal<>();

	public static void putSession(CommonSession commonSession) {
		sessionPools.set(commonSession);
	}

	public static void removeSession() {
		sessionPools.remove();
	}

	/**
	 * 获取对象
	 * 
	 * @return
	 *
	 */
	public static CommonSession getSession() {
		CommonSession session = sessionPools.get();
		if (session != null) {
			return session;
		}
		synchronized (sessionPools) {
			session = sessionPools.get();
			if (session != null) {
				return session;
			}
			session = new CommonSession();
			putSession(session);
		}
		return session;
	}
}
