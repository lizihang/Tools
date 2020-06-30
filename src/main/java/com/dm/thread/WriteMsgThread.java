package com.dm.thread;

import com.dm.queue.ProgressQueue;

import javax.swing.*;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月25日 21:56</p>
 * <p>类全名：com.dm.thread.WriteMsgThread</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class WriteMsgThread extends Thread
{
	private JTextArea area;

	public WriteMsgThread(JTextArea area)
	{
		this.area = area;
	}

	@Override
	public void run()
	{
		area.setText("");
		while (true)
		{
			String msg = ProgressQueue.getInstance().getMsg();
			area.append(msg + "\r\n");
			area.setCaretPosition(area.getDocument().getLength());
			if (msg.contains("功能完成"))
			{
				break;
			}
		}
	}
}
