package cn.sunshine.common.config;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import cn.sunshine.common.method.DeleteAll;

/**
 * <p>
 * 自定义 SqlInjector
 * </p>
 *
 * @author wuj
 * @since 2020年3月15日
 */
@Component
public class MyLogicSqlInjector extends DefaultSqlInjector {

	/**
	 * 如果只需增加方法，保留MP自带方法 可以super.getMethodList() 再add
	 * 
	 * @return
	 */
	@Override
	public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
		List<AbstractMethod> methodList = super.getMethodList(mapperClass);
		methodList.add(new DeleteAll());
		return methodList;
	}
}
