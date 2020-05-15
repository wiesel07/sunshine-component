package cn.sunshine.component.attach.service;

import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import cn.sunshine.component.common.exception.SunshineException;

/**
 * <p>
 * 父类上传下载公共服务接口
 * </p>
 *
 * @author wuj
 * @since 2020年3月15日
 */
public interface IAttachmentService {

	/**
	 * 利用MultipartFile 进行文件上传
	 * 
	 * @param attachId
	 * @param multipartFile
	 * @param bizType
	 * @param suffix
	 * @return
	 * @throws SunshineException
	 *
	 */
	String upload(String attachId, MultipartFile multipartFile, String bizType, String suffix) throws SunshineException;

	/*
	 * upload 文件上传
	 */
	String upload(String attachId, InputStream inputStream, String bizType, String suffix) throws SunshineException;

	/*
	 * upload 文件上传
	 */
	String upload(String attachId, File file, String bizType, String suffix) throws SunshineException;

	/**
	 * 文件删除
	 * 
	 * @param filePath
	 * @param attachId
	 * @return
	 *
	 */
	boolean delete(String filePath, Long attachId);

	InputStream dowload(String filePath, Long attachId);


	/**
	 * 处理类型
	 * @return
	 *
	 */
	 String getType();

	/**
	 * 返回文件上传目录
	 * 
	 * @return
	 *
	 */
	String getPath();
}
