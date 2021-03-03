package com.dm.factory;

import com.dm.windows.FileCopyPage;
import com.dm.windows.FileDeletePage;
import com.dm.windows.FileDownloadPage;
import com.dm.windows.FileRenamePage;

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

	static
	{
		// TODO 第一次初始化很慢
		containerMap.put("文件复制", new FileCopyPage());
		containerMap.put("文件删除", new FileDeletePage());
		containerMap.put("文件重命名", new FileRenamePage());
		containerMap.put("文件下载", new FileDownloadPage());
	}

	private PageFactory()
	{
	}

	private static final Container EMPTY = new Container();

	//获取
	public static Container getPage(String text)
	{
		Container result = containerMap.get(text);
		return result == null ? EMPTY : result;
	}

	//将处罚对象注册到这里
	public static void registerStrategy(String text, Container c)
	{
		containerMap.put(text, c);
	}
}
