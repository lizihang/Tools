package com.dm.data;

import com.dm.constant.DomConstant;
import com.dm.queue.ProgressQueue;

import java.io.*;
import java.util.*;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月26日 21:17</p>
 * <p>类全名：com.dm.data.DomResData</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class DomResData
{
	private                 Map<String,String> fColMap = new HashMap<>();
	private                 Map<String,String> btnMap  = new HashMap<>();
	private                 List<String>       files   = new ArrayList<>();
	private                 List<String>       mfiles  = new ArrayList<>();
	private volatile static DomResData         resData;

	private DomResData()
	{
	}

	public static DomResData getInstance()
	{
		if (resData == null)
		{
			synchronized (DomResData.class)
			{
				if (resData == null)
				{
					resData = new DomResData();
				}
			}
		}
		return resData;
	}

	public void init(String resPath, String filePath)
	{
		initRes(resPath);
		initFiles(filePath);
	}

	/**
	 * 初始化资源文件
	 * @param resPath 资源文件所在路径
	 */
	private void initRes(String resPath)
	{
		fColMap.clear();
		btnMap.clear();
		try
		{
			File file = new File(resPath);
			//中文乱码
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader input = new BufferedReader(isr);
			String text;
			boolean fCol = false;
			while ((text = input.readLine()) != null)
			{
				// 1.处理固定列
				if (text.contains("固定列开始"))
				{
					fCol = true;
				}
				if (text.contains("固定列结束"))
				{
					fCol = false;
				}
				if (fCol && text.contains("="))
				{
					fColMap.put(text.split("=")[0], text.split("=")[1]);
				}
				// 2.处理按钮map
				if (text.startsWith("title.F.btn"))
				{
					//反向map，将资源文件值作为key，资源文件键作为value
					btnMap.put(text.split("=")[1], text.split("=")[0]);
				}
			}
		} catch (Exception e)
		{
			//TODO 打印错误信息
			ProgressQueue.getInstance().putMsg("错误信息:" + Arrays.toString(e.getStackTrace()));
			System.out.println(Arrays.toString(e.getStackTrace()));
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
	}

	/**
	 * 初始化文件列表
	 * @param filePath 文件所在路径
	 */
	private void initFiles(String filePath)
	{
		files.clear();
		mfiles.clear();
		getAllFiles(filePath);
		getAllModifyFiles(filePath);
	}

	private void getAllFiles(String path)
	{
		File root = new File(path);
		if (root.isFile())
		{
			files.add(root.getAbsolutePath());
			return;
		}
		File[] folder = root.listFiles();
		if (folder == null)
		{
			ProgressQueue.getInstance().putMsg(root.getAbsolutePath() + "目录下没有内容");
			System.out.println("该文件夹下没有内容！");
		}
		for (File f : folder)
		{
			if (f.isDirectory())
			{
				getAllFiles(f.getAbsolutePath());
			} else
			{
				String fileType = f.getName().substring(f.getName().lastIndexOf(".") + 1);
				if (fileType.equals(DomConstant.FILE_SUFFIX) && !f.getAbsolutePath().contains("-new") && !f.getAbsolutePath().contains("-bak"))
				{
					files.add(f.getAbsolutePath());
				}
			}
		}
	}

	private void getAllModifyFiles(String path)
	{
		File root = new File(path);
		if (root.isFile())
		{
			mfiles.add(root.getAbsolutePath());
			return;
		}
		File[] folder = root.listFiles();
		if (folder == null)
		{
			ProgressQueue.getInstance().putMsg(path + "目录下没有内容");
			System.out.println("该文件夹下没有内容！");
		}
		for (File f : folder)
		{
			if (f.isDirectory())
			{
				getAllModifyFiles(f.getAbsolutePath());
			} else
			{
				String fileType = f.getName().substring(f.getName().lastIndexOf(".") + 1);
				if (fileType.equals(DomConstant.FILE_SUFFIX) && f.getAbsolutePath().contains("-new"))
				{
					mfiles.add(f.getAbsolutePath());
				}
			}
		}
	}

	public Map<String,String> getfColMap()
	{
		return fColMap;
	}

	public Map<String,String> getBtnMap()
	{
		return btnMap;
	}

	public List<String> getFiles()
	{
		return files;
	}

	public List<String> getMfiles()
	{
		return mfiles;
	}
}