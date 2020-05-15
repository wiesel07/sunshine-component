package cn.sunshine.common.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * <p>
 * 自定义填充器
 * </p>
 *
 * @author wuj
 * @since 2020年3月15日
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		// 新增时需要填充字段
		setFieldValByName("createTime", new Date(), metaObject);
		setFieldValByName("updateTime", new Date(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// 更新时 需要填充字段
		setFieldValByName("updateTime", new Date(), metaObject);
	}

}
