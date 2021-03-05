package com.dm.factory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2021年03月03日 16:56</p>
 * <p>类全名：com.dm.factory.PageFactory</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class PageFactory
{
	private static Map<String,Container> containerMap = new HashMap<>();
	private static Map<String,String>    pageMap      = new HashMap<>();

	static
	{
		// 1.文件相关
		pageMap.put("文件复制", "com.dm.windows.FileCopyPage");
		pageMap.put("文件删除", "com.dm.windows.FileDeletePage");
		pageMap.put("文件重命名", "com.dm.windows.FileRenamePage");
		pageMap.put("文件下载", "com.dm.windows.FileDownloadPage");
		// 2.xml相关
		pageMap.put("xml属性查询", "com.dm.windows.XmlAttrQueryPage");
		pageMap.put("修改title", "com.dm.windows.ModifyTitlePage");
		pageMap.put("修改属性", "com.dm.windows.ModifyPropPage");
		// 3.about
		pageMap.put("更新日志", "com.dm.windows.AboutPage");
	}

	private PageFactory()
	{
	}

	//获取
	public static Container getPage(String text)
	{
		Container container = containerMap.get(text);
		if (container == null)
		{
			container = getPageObject(text);
			containerMap.put(text, container);
		}
		return container;
	}

	private static Container getPageObject(String text)
	{
		String className = pageMap.get(text);
		try
		{
			return (Container) Class.forName(className).newInstance();
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
		{
			throw new RuntimeException(e);
		}
	}
}
