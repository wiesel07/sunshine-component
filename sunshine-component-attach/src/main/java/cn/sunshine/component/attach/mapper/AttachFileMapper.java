package cn.sunshine.component.attach.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.sunshine.component.attach.entity.AttachFile;

/**
 * <p>
 *  附件表 Mapper 接口
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
public interface AttachFileMapper extends BaseMapper<AttachFile> {

	

	int insertAttachFileInfo(@Param("fileInfo") AttachFile fileInfo,@Param("tableName") String tableName);


	@Delete(" delete FROM ${table}  WHERE ATTACH_ID = #{id}")
	int deleteAttachFileInfo(@Param("id") Long id,@Param("table") String tableName);
}
 