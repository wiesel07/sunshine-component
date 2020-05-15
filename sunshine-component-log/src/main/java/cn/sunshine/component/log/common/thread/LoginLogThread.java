package cn.sunshine.component.log.common.thread;

import cn.sunshine.component.log.biz.entity.SysLoginLog;
import cn.sunshine.component.log.biz.service.ISysLoginLogService;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月11日
 */
public class LoginLogThread implements Runnable {

	private SysLoginLog loginLog;
	private ISysLoginLogService loginLogService;

	public LoginLogThread(SysLoginLog loginLog, ISysLoginLogService loginLogService) {

		this.loginLog = loginLog;
		this.loginLogService = loginLogService;
	}

	@Override
	public void run() {
		this.loginLogService.save(loginLog);
	}

}
