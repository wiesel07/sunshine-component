package cn.sunshine.component.attach.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.sunshine.component.attach.entity.AttachFilestore;

/**
 * <p>
 *  文件存储表 Mapper 接口
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
public interface AttachFilestoreMapper extends BaseMapper<AttachFilestore> {

	 void saveFiles(@Param("attachId") String attachId,@Param("content") byte[] content);
}
 