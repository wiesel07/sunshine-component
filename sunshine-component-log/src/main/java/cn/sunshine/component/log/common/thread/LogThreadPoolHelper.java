package cn.sunshine.component.log.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
*
* @ClassName   类名：LogThreadPoolHelper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2018年12月23日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2018年12月23日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class LogThreadPoolHelper {
	
	public static ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(30);

}
