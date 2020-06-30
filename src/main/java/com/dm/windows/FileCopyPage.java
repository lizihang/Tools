package com.dm.windows;

import com.dm.constant.CommonConstant;
import com.dm.constant.WindowsConstant;
import com.dm.listener.ChooseButtonListener;
import com.dm.thread.FileCopyThread;
import com.dm.thread.WriteMsgThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
/**
 * <p>标题：文件拷贝页</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年04月06日 15:11</p>
 * <p>类全名：com.dm.windows.FileCopyPage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class FileCopyPage extends Container
{
	private static final long serialVersionUID = 7144529904668773187L;
	JLabel            label0   = new JLabel("将选中文件夹下的所有选中类型的文件复制到目标文件夹");//功能说明
	JLabel            label1   = new JLabel("选择目录");
	JLabel            label2   = new JLabel("目标目录");
	JLabel            label3   = new JLabel("选择格式");
	JTextField        text1    = new JTextField();
	JTextField        text2    = new JTextField();
	JTextArea         area     = new JTextArea(3, 20);//构造一个文本域
	JComboBox<String> comboBox = new JComboBox<>();
	JButton           button1  = new JButton("选择");
	JButton           button2  = new JButton("选择");
	JButton           execute  = new JButton("执行");
	JFileChooser      jfc      = new JFileChooser();//文件选择器

	public FileCopyPage()
	{
		jfc.setCurrentDirectory(new File("D:\\"));//文件选择器的初始目录定为d盘
		label0.setBounds(10, 10, 400, WindowsConstant.COMMON_HEIGHT);
		label1.setBounds(10, 40, WindowsConstant.LABEL_WIDTH, WindowsConstant.COMMON_HEIGHT);
		label2.setBounds(10, 70, WindowsConstant.LABEL_WIDTH, WindowsConstant.COMMON_HEIGHT);
		label3.setBounds(10, 100, WindowsConstant.LABEL_WIDTH, WindowsConstant.COMMON_HEIGHT);
		text1.setBounds(80, 40, WindowsConstant.TEXT_WIDTH, WindowsConstant.COMMON_HEIGHT);
		text1.setEditable(false);
		text2.setBounds(80, 70, WindowsConstant.TEXT_WIDTH, WindowsConstant.COMMON_HEIGHT);
		text2.setEditable(false);
		comboBox.setBounds(80, 100, WindowsConstant.BOX_WIDTH, WindowsConstant.COMMON_HEIGHT);
		comboBox.addItem("xml");
		comboBox.addItem("txt");
		comboBox.addItem("exe");
		comboBox.addItem("jar");
		button1.setBounds(290, 40, WindowsConstant.BUTTON_WIDTH, WindowsConstant.COMMON_HEIGHT);
		button2.setBounds(290, 70, WindowsConstant.BUTTON_WIDTH, WindowsConstant.COMMON_HEIGHT);
		execute.setBounds(290, 100, WindowsConstant.BUTTON_WIDTH, WindowsConstant.COMMON_HEIGHT);
		area.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
		JScrollPane scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 150, WindowsConstant.AREA_WIDTH, WindowsConstant.AREA_HEIGHT);
		//选择路径按钮的监听
		button1.addActionListener(new ChooseButtonListener(jfc, text1, JFileChooser.DIRECTORIES_ONLY));
		button2.addActionListener(new ChooseButtonListener(jfc, text2, JFileChooser.DIRECTORIES_ONLY));
		//执行按钮的监听
		execute.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String from = text1.getText();
				String to = text2.getText();
				if (from.length() == 0 || to.length() == 0)
				{
					//ERR:请选择目录!
					JOptionPane.showMessageDialog(null, CommonConstant.ERR00001);
					return;
				}
				String suffix = comboBox.getSelectedItem().toString();
				new WriteMsgThread(area).start();
				new FileCopyThread(from, to, suffix).start();
			}
		});
		this.add(label0);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(text1);
		this.add(text2);
		this.add(comboBox);
		this.add(button1);
		this.add(button2);
		this.add(execute);
		this.add(scrollPane);
		this.add(jfc);
	}
}
