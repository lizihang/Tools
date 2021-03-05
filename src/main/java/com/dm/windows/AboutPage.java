package com.dm.windows;

import com.dm.constant.DmConstants;

import javax.swing.*;
import java.awt.*;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2021年03月05日 10:50</p>
 * <p>类全名：com.dm.windows.AboutPage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class AboutPage extends Container
{
	JTextArea area = new JTextArea(3, 20);//构造一个文本域

	public AboutPage()
	{
		area.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
		area.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 10, DmConstants.WIDTH_AREA, DmConstants.HEIGHT_AREA);
		this.add(scrollPane);
	}
}
