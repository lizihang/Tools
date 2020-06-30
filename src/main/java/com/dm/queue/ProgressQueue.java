package com.dm.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
 * <p>标题：队列</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年04月06日 19:39</p>
 * <p>类全名：com.dm.queue.ProgressQueue</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class ProgressQueue
{
	/** ProgressQueue类对象的单例模式 */
	private volatile static ProgressQueue         instance;
	/** 消息队列 */
	private                 BlockingQueue<String> msgQueue = new ArrayBlockingQueue<>(20);

	public static ProgressQueue getInstance()
	{
		if (instance == null)
		{
			synchronized (ProgressQueue.class)
			{
				if (instance == null)
				{
					instance = new ProgressQueue();
				}
			}
		}
		return instance;
	}

	/**
	 * 将消息放入队列
	 * @param msg
	 */
	public void putMsg(String msg)
	{
		try
		{
			msgQueue.put(msg);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取消息
	 * @return
	 */
	public String getMsg()
	{
		String msg = "";
		try
		{
			msg = msgQueue.take();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return msg;
	}
}
