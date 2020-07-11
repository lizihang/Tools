package com.dm.service;

/**
 * <p>标题：用户登录service</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：暂时模拟登录，以后增加个sso服务，然后调用返回结果
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年07月11日 15:13</p>
 * <p>类全名：com.dm.service.LoginService</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class LoginService
{
	public boolean login(String username, String password)
	{
		// TODO
		if ("admin".equals(username) && "111111".equals(password))
		{
			return true;
		} else
		{
			return false;
		}
	}
}
