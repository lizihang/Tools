package com.dm.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
/**
 * <p>标题：文件下载工具，待优化</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年04月06日 15:16</p>
 * <p>类全名：com.dm.util.DownloadFile</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class DownloadFile
{
	static long length = 0;
	static long size   = 0;

	/**
	 * 下载文件
	 * @param url
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadFile(String url, String fileName) throws Exception
	{
		//		url = "http://dl.download.csdn.net/down10/20111102/dafd2c34d4596790649a42d1a2d078db.rar?response-content-disposition=attachment%3Bfilename%3D%22%E5%A5%BD%E7%8E%A9.rar%22&OSSAccessKeyId=9q6nvzoJGowBj4q1&Expires=1490780377&Signature=2Zd4hHTiP5gOp99h%2BX7RVBtzvjU%3D";
		//		fileName = "d:/1.zip";
		URL u = new URL(url);
		System.out.println(u.getFile());
		InputStream in = u.openStream();
		size = (u.openConnection().getContentLength()); // (1024 * 1024);
		System.out.println(size);
		new Thread(new Runnable()
		{
			long download = 0;

			@Override
			public void run()
			{
				do
				{
					download = length;
					System.out.println(download + "----" + size);
					double percent = (double) ((download * 10000) / size) / 100;
					System.out.println("下载已完成：" + percent + "%");
				} while (download != size);
			}
		}).start();
		BufferedOutputStream outfile = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
		byte[] buffer = new byte[8192];// 字节数组 存放读取的文件的数据构造方法
		int i = in.read(buffer);
		//System.out.println(i);
		while (i != -1)
		{
			length += i;
			outfile.write(buffer, 0, i);
			outfile.flush();
			i = in.read(buffer);
		}
		outfile.flush();
		in.close();
		outfile.close();
	}

	public static void downloadFile1(String url, String fileName) throws Exception
	{
		URL u = new URL(url);
		InputStream in = u.openStream();
		size = (u.openConnection().getContentLength()); // (1024 * 1024);
		BufferedOutputStream outfile = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
		byte[] buffer = new byte[8192];// 字节数组 存放读取的文件的数据构造方法
		int i = in.read(buffer);
		//System.out.println(i);
		while (i != -1)
		{
			length += i;
			outfile.write(buffer, 0, i);
			outfile.flush();
			i = in.read(buffer);
		}
		outfile.flush();
		in.close();
		outfile.close();
	}

	public static void main(String[] args) throws Exception
	{
		downloadFile("http://sw.bos.baidu.com/sw-search-sp/software/52cf854cdb1b6/QQMusic_13.12.3813.329.exe", "d:/QQMusic_13.12.3813.329.exe");
	}
}
