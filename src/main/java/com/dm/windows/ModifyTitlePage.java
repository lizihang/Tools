package com.dm.windows;

import com.dm.constant.DmConstants;
import com.dm.listener.ChooseButtonListener;
import com.dm.thread.ModifyTitleThread;
import com.dm.thread.WriteMsgThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月25日 16:49</p>
 * <p>类全名：com.dm.windows.ModifyTitlePage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class ModifyTitlePage extends Container
{
	JLabel       label0  = new JLabel("资源文件已自动加载ResBundleFT-COMM_zh_CN.inf");//功能说明
	JLabel       label1  = new JLabel("选择资源文件");
	JLabel       label2  = new JLabel("选择文件目录");
	JTextField   text1   = new JTextField();
	JTextField   text2   = new JTextField();
	JTextArea    area    = new JTextArea(3, 20);//构造一个文本域
	JButton      button1 = new JButton("选择");
	JButton      button2 = new JButton("选择");
	JButton      execute = new JButton("执行");
	JFileChooser jfc     = new JFileChooser();//文件选择器

	public ModifyTitlePage()
	{
		// jfc.setCurrentDirectory(new File("D:/snsoft90/sn_ft"));//文件选择器的初始目录定为d盘
		jfc.setCurrentDirectory(new File("D:/snsoft90/sn_ft/ft-sna/ft-sna/sna-ui/src/main/resources/cfg/ui/res/FT-SNA"));//文件选择器的初始目录定为d盘
		label0.setBounds(10, 10, 400, DmConstants.HEIGHT_COMMON);
		label1.setBounds(10, 40, DmConstants.WIDTH_LABEL + 20, DmConstants.HEIGHT_COMMON);
		label2.setBounds(10, 70, DmConstants.WIDTH_LABEL + 20, DmConstants.HEIGHT_COMMON);
		text1.setBounds(80 + 20, 40, DmConstants.WIDTH_TEXT - 20, DmConstants.HEIGHT_COMMON);
		text1.setEditable(false);
		text1.setText("D:/snsoft90/sn_ft/ft-sna/ft-sna/sna-ui/src/main/resources/cfg/resbundle/ResBundleFT-SNA_zh_CN.inf");
		text2.setBounds(80 + 20, 70, DmConstants.WIDTH_TEXT - 20, DmConstants.HEIGHT_COMMON);
		text2.setEditable(false);
		button1.setBounds(290, 40, DmConstants.WIDTH_BUTTON, DmConstants.HEIGHT_COMMON);
		button2.setBounds(290, 70, DmConstants.WIDTH_BUTTON, DmConstants.HEIGHT_COMMON);
		execute.setBounds(290, 100, DmConstants.WIDTH_BUTTON, DmConstants.HEIGHT_COMMON);
		area.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
		JScrollPane scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 150, DmConstants.WIDTH_AREA, DmConstants.HEIGHT_AREA);
		//选择路径按钮的监听
		button1.addActionListener(new ChooseButtonListener(jfc, text1, JFileChooser.FILES_ONLY));
		button2.addActionListener(new ChooseButtonListener(jfc, text2, JFileChooser.FILES_AND_DIRECTORIES));
		//执行按钮的监听
		execute.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String resPath = text1.getText();
				String filePath = text2.getText();
				if (resPath.length() == 0 || filePath.length() == 0)
				{
					//ERR:请选择目录!
					JOptionPane.showMessageDialog(null, DmConstants.ERR00002);
					return;
				}
				new WriteMsgThread(area).start();
				new ModifyTitleThread(resPath, filePath).start();
			}
		});
		this.add(label0);
		this.add(label1);
		this.add(label2);
		this.add(text1);
		this.add(text2);
		this.add(button1);
		this.add(button2);
		this.add(execute);
		this.add(scrollPane);
		this.add(jfc);
	}
}
