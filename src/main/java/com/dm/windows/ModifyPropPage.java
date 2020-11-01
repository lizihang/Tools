package com.dm.windows;

import com.dm.constant.CommonConstant;
import com.dm.constant.WindowsConstant;
import com.dm.listener.ChooseButtonListener;
import com.dm.thread.ModifyPropThread;
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
 * <p>创建日期：2020年10月31日 15:21</p>
 * <p>类全名：com.dm.windows.PropSetPage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class ModifyPropPage extends Container
{
	JLabel            label0   = new JLabel("修改标签属性，Json格式");//功能说明
	JLabel            label1   = new JLabel("选择文件目录");
	JLabel            label2   = new JLabel("选择标签类型");
	JLabel            label3   = new JLabel("设置标签属性");
	JTextField        text1    = new JTextField();
	JComboBox<String> comboBox = new JComboBox<>();
	JTextField        text2    = new JTextField();
	JTextArea         area     = new JTextArea(3, 20);//构造一个文本域
	JButton           button1  = new JButton("选择");
	JButton           execute  = new JButton("执行");
	JFileChooser      jfc      = new JFileChooser();//文件选择器

	public ModifyPropPage()
	{
		{
			// jfc.setCurrentDirectory(new File("D:/snsoft90/sn_ft"));//文件选择器的初始目录定为d盘
			jfc.setCurrentDirectory(new File("D:/snsoft90/sn_ft/ft-sna/ft-sna/sna-ui/src/main/resources/cfg/ui/res/FT-SNA"));//文件选择器的初始目录定为d盘
			label0.setBounds(10, 10, 400, WindowsConstant.COMMON_HEIGHT);
			label1.setBounds(10, 40, WindowsConstant.LABEL_WIDTH + 20, WindowsConstant.COMMON_HEIGHT);
			label2.setBounds(10, 70, WindowsConstant.LABEL_WIDTH + 20, WindowsConstant.COMMON_HEIGHT);
			label3.setBounds(10, 100, WindowsConstant.LABEL_WIDTH + 20, WindowsConstant.COMMON_HEIGHT);
			text1.setBounds(80 + 20, 40, WindowsConstant.TEXT_WIDTH - 20, WindowsConstant.COMMON_HEIGHT);
			text1.setEditable(false);
			text1.setText("D:/snsoft90/sn_ft/ft-sna/ft-sna/sna-ui/src/main/resources/cfg/ui/res/FT-SNA");
			comboBox.setBounds(80 + 20, 70, WindowsConstant.BOX_WIDTH - 20, WindowsConstant.COMMON_HEIGHT);
			comboBox.addItem("c");
			comboBox.addItem("ToolbarBtn");
			comboBox.addItem("attr");
			comboBox.addItem("Operate");
			comboBox.addItem("GridTable");
			comboBox.addItem("DialogPane");
			comboBox.addItem("Toolbar");
			comboBox.addItem("RecordTable");
			text2.setBounds(80 + 20, 100, WindowsConstant.TEXT_WIDTH - 20, WindowsConstant.COMMON_HEIGHT);
			button1.setBounds(290, 40, WindowsConstant.BUTTON_WIDTH, WindowsConstant.COMMON_HEIGHT);
			execute.setBounds(290, 100, WindowsConstant.BUTTON_WIDTH, WindowsConstant.COMMON_HEIGHT);
			area.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
			JScrollPane scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(10, 150, WindowsConstant.AREA_WIDTH, WindowsConstant.AREA_HEIGHT);
			//选择路径按钮的监听
			button1.addActionListener(new ChooseButtonListener(jfc, text1, JFileChooser.FILES_ONLY));
			//执行按钮的监听
			execute.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					String filePath = text1.getText();
					String tagName = comboBox.getSelectedItem().toString();
					String props = text2.getText().trim();
					if (filePath.length() == 0)
					{
						//ERR:请选择目录!
						JOptionPane.showMessageDialog(null, CommonConstant.ERR00002);
						return;
					}
					if (tagName == null || tagName.length() == 0)
					{
						//ERR:请设置标签属性!
						JOptionPane.showMessageDialog(null, CommonConstant.ERR00003);
						return;
					}
					new WriteMsgThread(area).start();
					new ModifyPropThread(filePath, tagName, props).start();
				}
			});
			this.add(label0);
			this.add(label1);
			this.add(label2);
			this.add(label3);
			this.add(text1);
			this.add(comboBox);
			this.add(text2);
			this.add(button1);
			this.add(execute);
			this.add(scrollPane);
			this.add(jfc);
		}
	}
}
