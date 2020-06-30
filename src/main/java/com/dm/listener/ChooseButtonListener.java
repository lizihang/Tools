package com.dm.listener;

import javax.swing.*;
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
 * <p>创建日期：2020年04月06日 20:28</p>
 * <p>类全名：com.dm.listener.ChooseButtonListener</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class ChooseButtonListener implements ActionListener
{
	JFileChooser jfc;
	JTextField   text;
	int          selectMode;

	public ChooseButtonListener(JFileChooser jfc, JTextField text, int selectMode)
	{
		this.jfc = jfc;
		this.text = text;
		this.selectMode = selectMode;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		//JFileChooser.DIRECTORIES_ONLY
		jfc.setFileSelectionMode(selectMode);//设定只能选择到文件夹
		int state = jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
		if (state == 1)
		{
			return;//撤销则返回
		} else
		{
			text.setText(jfc.getSelectedFile().getAbsolutePath());
		}
	}
}
