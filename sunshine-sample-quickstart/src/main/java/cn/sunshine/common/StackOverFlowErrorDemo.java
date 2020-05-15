package cn.sunshine.common;

/**
 * <p>
 * 虚拟机栈溢出异常
 * </p>
 *
 * @author wuj
 * @since 2020年3月21日
 */
public class StackOverFlowErrorDemo {

 
	public void stackLeakByThread() {
		while (true) {
			Thread thread =new Thread(new Runnable() {
				@Override
				public void run() {
					while (true){}
				}
			});
			thread.start();
		}
	}
}
