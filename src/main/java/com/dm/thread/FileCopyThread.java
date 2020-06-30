package com.dm.thread;

import com.dm.constant.CommonConstant;
import com.dm.queue.ProgressQueue;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月26日 14:36</p>
 * <p>类全名：com.dm.thread.FileCopyThread</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class FileCopyThread extends Thread
{
	private String fileFrom;
	private String fileTo;
	private String suffix;

	public FileCopyThread(String fileFrom, String fileTo, String suffix)
	{
		this.fileFrom = fileFrom;
		this.fileTo = fileTo;
		this.suffix = suffix;
	}

	@Override
	public void run()
	{
		System.out.println(String.format(CommonConstant.START_MSG,"文件复制"));
		ProgressQueue.getInstance().putMsg(String.format(CommonConstant.START_MSG,"文件复制"));
		/**
		 * 参数1：源路径
		 * 参数2：目标路径
		 * 参数3：过滤条件，需要拼接文件夹过滤和文件过滤
		 */
		// Create a filter for "xml" files
		IOFileFilter suffixFilter = FileFilterUtils.suffixFileFilter(suffix);
		IOFileFilter xmlFiles = FileFilterUtils.and(FileFileFilter.FILE, suffixFilter);

		// Create a filter for either directories or ".txt" files
		FileFilter filter = FileFilterUtils.or(DirectoryFileFilter.DIRECTORY, xmlFiles);
		// Copy using the filter
		try
		{
			FileUtils.copyDirectory(new File(fileFrom),new File(fileTo), filter);
		} catch (IOException e)
		{
			ProgressQueue.getInstance().putMsg(Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
		System.out.println(String.format(CommonConstant.END_MSG,"文件复制"));
		ProgressQueue.getInstance().putMsg(String.format(CommonConstant.END_MSG,"文件复制"));
	}
}
