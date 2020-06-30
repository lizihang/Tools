package com.dm.windows;

import com.dm.listener.ChooseButtonListener;
import com.dm.util.DownloadFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
/**
 * <p>标题：文件下载页</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年04月06日 15:09</p>
 * <p>类全名：com.dm.windows.FileDownloadPage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class FileDownloadPage extends Container
{
	private static final long serialVersionUID = -6290874124743113305L;
	//文件下载页
	JLabel       label0  = new JLabel("下载文件说明等等");//功能说明
	JLabel       label1  = new JLabel("请输入下载地址");
	JLabel       label2  = new JLabel("请选择保存路径");
	JLabel       label3  = new JLabel("文件名称");
	JTextField   text1   = new JTextField();
	JTextField   text2   = new JTextField();
	JTextField   text3   = new JTextField();
	JTextArea    area    = new JTextArea(3, 20);//构造一个文本域
	JButton      button  = new JButton("选择");
	JButton      execute = new JButton("执行");
	JFileChooser jfc     = new JFileChooser();//文件选择器

	public FileDownloadPage()
	{
		jfc.setCurrentDirectory(new File("E:\\"));//文件选择器的初始目录定为d盘
		label0.setBounds(10, 10, 400, 20);
		label1.setBounds(10, 40, 100, 20);
		label2.setBounds(10, 70, 100, 20);
		label3.setBounds(10, 100, 100, 20);
		text1.setBounds(110, 40, 260, 20);
		text2.setBounds(110, 70, 170, 20);
		text3.setBounds(110, 100, 170, 20);
		button.setBounds(290, 70, 80, 20);
		execute.setBounds(290, 100, 80, 20);
		area.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
		JScrollPane scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 150, 360, 200);
		//选择路径按钮的监听
		button.addActionListener(new ChooseButtonListener(jfc, text2, JFileChooser.DIRECTORIES_ONLY));
		//执行按钮的监听
		execute.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String downloadURL = text1.getText();
				String savePath = text2.getText();
				String fileName = text3.getText();
				if (downloadURL.length() == 0 || savePath.length() == 0 || fileName.length() == 0)
				{
					JOptionPane.showMessageDialog(null, "请选择文件位置");
				} else
				{
					try
					{
						DownloadFile.downloadFile(downloadURL, savePath + File.separator + fileName);
						area.setText("下载完成！");
					} catch (Exception e1)
					{
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		});
		this.add(label0);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(text1);
		this.add(text2);
		this.add(text3);
		this.add(button);
		this.add(execute);
		this.add(scrollPane);
	}
}
