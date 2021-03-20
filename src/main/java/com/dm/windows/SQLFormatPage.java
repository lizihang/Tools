package com.dm.windows;

import com.dm.constant.DmConstants;
import com.dm.thread.SQLFormatThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2021年03月20日 21:51</p>
 * <p>类全名：com.dm.windows.SQLFormatPage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class SQLFormatPage extends Container
{
	JTextArea  source     = new JTextArea(3, 20);//构造一个文本域
	JTextArea  formatPage = new JTextArea(3, 20);//构造一个文本域
	JTextField formatLine = new JTextField();
	JTextField savePath   = new JTextField();
	JButton    button1    = new JButton("转换");
	JButton    button2    = new JButton("保存");

	public SQLFormatPage()
	{
		//文本域配置
		source.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
		JScrollPane sp1 = new JScrollPane(source, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp1.setBounds(10, 10, 370, 440);
		JScrollPane sp2 = new JScrollPane(formatPage, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp2.setBounds(400, 10, 370, 440);
		//文本配置
		formatLine.setBounds(100, 460, 660, 20);
		savePath.setBounds(100, 490, 660, 20);
		//按钮配置
		button1.setBounds(10, 460, 80, 20);
		button2.setBounds(10, 490, 80, 20);
		//添加事件处理
		button1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String sql = source.getText();
				if (sql == null || sql.length() == 0)
				{
					//ERR:请输入SQL！
					JOptionPane.showMessageDialog(null, DmConstants.ERR00006);
					return;
				}
				new SQLFormatThread(sql, formatPage, formatLine).start();
			}
		});
		//添加事件处理
		/*button2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String fileName = jt.getText().trim();
				if (fileName.length() == 0)
				{
					JOptionPane.showMessageDialog(frame, "请输入文件名称！");
				} else
				{
					if (WriteSQLToFile.writeSQLToFile(FSQLConstants.path, fileName, SQL))
					{
						JOptionPane.showMessageDialog(frame, "保存成功");
					} else
					{
						JOptionPane.showMessageDialog(frame, "保存失败");
					}
				}
			}
		});*/
		this.add(sp1);
		this.add(sp2);
		this.add(formatLine);
		this.add(savePath);
		this.add(button1);
		this.add(button2);
	}
}
