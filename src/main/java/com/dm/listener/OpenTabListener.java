package com.dm.listener;

import com.dm.factory.PageFactory;

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
 * <p>创建日期：2021年03月03日 16:31</p>
 * <p>类全名：com.dm.listener.OpenTabListener</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class OpenTabListener implements ActionListener
{
	JTabbedPane tabPane;
	JMenuItem   menu;

	public OpenTabListener(JTabbedPane tabPane, JMenuItem menu)
	{
		this.tabPane = tabPane;
		this.menu = menu;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Container c = PageFactory.getPage(menu.getText());
		Component[] components = tabPane.getComponents();
		boolean exist = false;
		for (Component com : components)
		{
			if (com.equals(c))
			{
				exist = true;
			}
		}
		if (!exist)
		{
			tabPane.add(menu.getText(), c);
		}
		tabPane.setSelectedComponent(c);
	}
}
