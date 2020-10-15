package com.dm.windows;

import com.dm.listener.ChooseButtonListener;
import com.dm.thread.FileDeleteThread;
import com.dm.thread.WriteMsgThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
/**
 * <p>标题：文件删除页</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年04月06日 15:11</p>
 * <p>类全名：com.dm.windows.FileDeletePage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class FileDeletePage extends Container
{
	private static final long serialVersionUID = 7562078686217685702L;
	JLabel       label0   = new JLabel("删除选中文件夹下的所有文件和文件夹");//功能说明
	JLabel       label1   = new JLabel("选择目录");
	JLabel       label2   = new JLabel("删除目录下所有文件");
	JLabel       label3   = new JLabel("正则");
	JTextField   text     = new JTextField();
	JTextField   text1    = new JTextField();
	JTextArea    area     = new JTextArea(3, 20);//构造一个文本域
	//JComboBox<String> comboBox = new JComboBox<String>();
	JButton      button   = new JButton("选择");
	JButton      execute  = new JButton("删除");
	JButton      execute1 = new JButton("执行");
	JFileChooser jfc      = new JFileChooser();//文件选择器

	public FileDeletePage()
	{
		jfc.setCurrentDirectory(new File("D:/snsoft90/sn_ft/ft-sna/ft-sna/sna-ui/src/main/resources/cfg/ui/res/FT-SNA"));//文件选择器的初始目录定为d盘
		label0.setBounds(10, 10, 400, 20);
		label1.setBounds(10, 40, 70, 20);
		label2.setBounds(10, 70, 400, 20);
		label3.setBounds(10, 100, 70, 20);
		text.setBounds(80, 40, 200, 20);
		text.setEditable(false);
		button.setBounds(290, 40, 80, 20);
		execute.setBounds(290, 70, 80, 20);
		execute1.setBounds(290, 100, 80, 20);
		text1.setBounds(80, 100, 200, 20);
		//comboBox.setBounds(80, 100, 200, 20);
		//comboBox.addItem("all");
		//comboBox.addItem("jar");
		area.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
		JScrollPane scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 150, 360, 200);
		//选择路径按钮的监听
		button.addActionListener(new ChooseButtonListener(jfc, text, JFileChooser.DIRECTORIES_ONLY));
		execute.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String filePath = text.getText();
				if (filePath.length() == 0)
				{
					JOptionPane.showMessageDialog(null, "请选择文件夹");
				} else
				{
					int returnCode = JOptionPane.showConfirmDialog(null, "是否删除" + filePath + "下的所有文件？");
					if (returnCode == 0)
					{
						new WriteMsgThread(area).start();
						new FileDeleteThread(filePath).start();
					}
				}
			}
		});
		execute1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String filePath = text.getText();
				String regex = text1.getText();
				if (filePath.length() == 0)
				{
					JOptionPane.showMessageDialog(null, "请选择文件夹");
				} else
				{
					int returnCode = JOptionPane.showConfirmDialog(null, "是否删除" + filePath + "下的所有符合正则的文件？");
					if (returnCode == 0)
					{
						new WriteMsgThread(area).start();
						new FileDeleteThread(filePath, regex).start();
					}
				}
			}
		});
		this.add(label0);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(text);
		this.add(button);
		this.add(execute);
		this.add(execute1);
		//this.add(comboBox);
		this.add(text1);
		this.add(scrollPane);
		this.add(jfc);
	}
}
