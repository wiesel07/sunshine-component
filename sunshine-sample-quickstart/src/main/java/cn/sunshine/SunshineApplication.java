package cn.sunshine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @date 2019年5月27日
 * @author wuj
 * @version V1.0
 */
@SpringBootApplication
@Slf4j
@EnableCaching
@EnableAsync
@ComponentScan(value = { "cn.sunshine" })
@EnableAspectJAutoProxy(exposeProxy = true)
public class SunshineApplication {

	public static void main(String[] args) {
//		set global time_zone = '+8:00'; ##修改mysql全局时区为北京时间，即我们所在的东8区
//		set time_zone = '+8:00'; ##修改当前会话时区
//		flush privileges; #立即生效
		SpringApplication.run(SunshineApplication.class, args);
		log.info("SunshineApplication工程启动");
		
//		List<String> classes = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, this.getClass().getClassLoader());
//		classes.forEach(clazz -> {
//		    System.out.println("==== " + clazz);
//		});
	}
	
}
