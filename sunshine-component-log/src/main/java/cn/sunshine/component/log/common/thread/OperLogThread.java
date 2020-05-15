package cn.sunshine.component.log.common.thread;

import cn.sunshine.component.log.biz.entity.SysOperLog;
import cn.sunshine.component.log.biz.service.ISysOperLogService;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月11日
 */
public class OperLogThread implements Runnable {

	private SysOperLog operLog;
	private ISysOperLogService operLogService;

	public OperLogThread(SysOperLog operLog,  ISysOperLogService operLogService) {

		this.operLog = operLog;
		this.operLogService = operLogService;
	}

	@Override
	public void run() {
		this.operLogService.save(operLog);
	}

}
