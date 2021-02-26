package com.dm.thread;

import com.dm.constant.CommonConstant;
import com.dm.queue.ProgressQueue;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月26日 21:07</p>
 * <p>类全名：com.dm.thread.FileDeleteThread</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class FileDeleteThread extends Thread
{
	private String filePath;
	private String regex;

	public FileDeleteThread(String filePath)
	{
		this(filePath, null);
	}

	public FileDeleteThread(String filePath, String regex)
	{
		this.filePath = filePath;
		this.regex = regex;
	}

	@Override
	public void run()
	{
		System.out.println(String.format(CommonConstant.START_MSG, "文件删除"));
		ProgressQueue.getInstance().putMsg(String.format(CommonConstant.START_MSG, "文件删除"));
		if (this.regex == null)
		{
			try
			{
				FileUtils.cleanDirectory(new File(filePath));
			} catch (IOException e)
			{
				ProgressQueue.getInstance().putMsg(Arrays.toString(e.getStackTrace()));
				e.printStackTrace();
			}
		} else
		{
			RegexFileFilter regexFileFilter = new RegexFileFilter("^.[a-zA-Z0-9_]+" + regex + ".xml$");
			Collection<File> files = FileUtils.listFiles(new File(filePath), regexFileFilter, DirectoryFileFilter.INSTANCE);
			for (File f : files)
			{
				try
				{
					FileUtils.forceDelete(f);
					System.out.println(f.getAbsolutePath() + "删除完成");
					ProgressQueue.getInstance().putMsg(f.getAbsolutePath() + "删除完成");
				} catch (IOException e)
				{
					System.out.println(f.getAbsolutePath() + "删除失败");
					ProgressQueue.getInstance().putMsg(f.getAbsolutePath() + "删除失败");
					//e.printStackTrace();
				}
			}
		}
		System.out.println(String.format(CommonConstant.END_MSG, "文件删除"));
		ProgressQueue.getInstance().putMsg(String.format(CommonConstant.END_MSG, "文件删除"));
	}
}
