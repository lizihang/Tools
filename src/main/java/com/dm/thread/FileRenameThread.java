package com.dm.thread;

import com.dm.constant.DmConstants;
import com.dm.data.DomResData;
import com.dm.queue.ProgressQueue;

import java.io.File;
import java.util.List;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月26日 12:52</p>
 * <p>类全名：com.dm.thread.FileRenameThread</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class FileRenameThread extends Thread
{
	private String filePath;
	private String target;
	private String replacement;

	public FileRenameThread(String filePath, String target, String replacement)
	{
		this.filePath = filePath;
		this.target = target;
		this.replacement = replacement;
	}

	@Override
	public void run()
	{
		// 0.初始化资源文件等数据
		DomResData resData = DomResData.getInstance();
		resData.init(null, filePath);
		ProgressQueue.getInstance().putMsg(String.format(DmConstants.START_MSG, "文件重命名"));
		List<String> files = resData.getFiles();
		List<String> mfiles = resData.getMfiles();
		rename(files, ".xml", "-bak.xml");
		rename(mfiles, "-new.xml", ".xml");
		ProgressQueue.getInstance().putMsg(String.format(DmConstants.END_MSG, "文件重命名"));
	}

	private void rename(List<String> files, String target, String replacement)
	{
		for (String fPath : files)
		{
			File f = new File(fPath);
			String newPath = fPath.replace(target, replacement);
			boolean result = f.renameTo(new File(newPath));
			if (result)
			{
				String msg = String.format("文件名修改成功！\r\n原名：%s\r\n旧名:%s", fPath, newPath);
				System.out.println(msg);
				ProgressQueue.getInstance().putMsg(msg);
			} else
			{
				String msg = String.format("文件名修改失败！\r\n原名：%s", fPath);
				System.out.println(msg);
				ProgressQueue.getInstance().putMsg(msg);
			}
		}
	}
}
