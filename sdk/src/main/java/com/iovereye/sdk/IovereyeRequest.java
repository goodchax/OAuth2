package com.iovereye.sdk;

import java.util.Map;

/**
 * TOP请求接口。
 * 
 * @author JieMo
 * @since 1.0, 05 14, 2014
 */
public interface IovereyeRequest<T extends IovereyeResponse> {
	
	/**
	 * 获取TOP的API类型。	 * 
	 * @return API类型
	 */
	public String getApiMethod();
	
	/**
	 * 获取TOP的地址。
	 * 格式：user.addUser 
	 * @return API名称
	 */
	public String getApiUri();

	/**
	 * 获取所有的Key-Value形式的文本请求参数集合。其中：
	 * <ul>
	 * <li>Key: 请求参数名</li>
	 * <li>Value: 请求参数值</li>
	 * </ul> 
	 * @return 文本请求参数集合
	 */
	public Map<String, String> getTextParams();

	/**
	 * @return 指定或默认的时间戳
	 */
	public Long getTimestamp();

	/**
	 * 设置时间戳，如果不设置,发送请求时将使用当时的时间。
	 * @param timestamp 时间戳
	 */
	public void setTimestamp(Long timestamp);

	/**
	 * 获取返回资源的类型
	 * @return
	 */
	public Class<T> getResponseClass();

	/**
	 * 客户端参数检查，减少服务端无效调用
	 */
	public void check() throws ApiRuleException;

	/**
	 * 添加HTTP请求头参数
	 */
	public Map<String, String> getHeaderMap();

	/**
	 * 添加自定义请求参数
	 */
	public void putOtherTextParam(String key, String value);
	
	/**
	 * 返回自定义请求参数
	 * @return
	 */
	public Map<String, String> getOtherTextParam();
	
}