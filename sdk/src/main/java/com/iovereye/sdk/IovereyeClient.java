package com.iovereye.sdk;

/**
 * API客户端。
 * 
 * @author Jie.mo
 * @since 1.0, Sep 12, 2014
 */
public interface IovereyeClient {

	/**
	 * 执行TOP公开API请求。 
	 * @param <T>
	 * @param request 具体的TOP请求
	 * @return
	 * @throws ApiException
	 */
	public <T extends IovereyeResponse> T execute(IovereyeRequest<T> request)
			throws ApiException;

	/**
	 * 执行TOP隐私API请求。 
	 * @param <T>
	 * @param request 具体的TOP请求
	 * @param session 用户会话授权码
	 * @return
	 * @throws ApiException
	 */
	public <T extends IovereyeResponse> T execute(IovereyeRequest<T> request,
			String session) throws ApiException;
}