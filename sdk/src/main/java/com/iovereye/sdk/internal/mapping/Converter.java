package com.iovereye.sdk.internal.mapping;

import com.iovereye.sdk.ApiException;
import com.iovereye.sdk.IovereyeResponse;


/**
 * 动态格式转换器
 * 
 * @author JieMo
 * @since 1.0, 06 3, 2014
 *
 */
public interface Converter {
	
	/**
	 * 把字符呼呼大睡 转换为响应对象
	 * 
	 * @param rsp 响应字符串
	 * @param clazz 领域类型
	 * @return 响应对象
	 * @throws ApiException
	 */
	public <T extends IovereyeResponse> T toResponse(String rsp, Class<T> clazz) throws ApiException;
}
