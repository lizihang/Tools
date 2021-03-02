package com.dm.windows;

import com.dm.listener.LoginButtonListener;

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
 * <p>创建日期：2020年07月11日 14:28</p>
 * <p>类全名：com.dm.windows.LoginPage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class LoginPage
{
	JFrame         frame  = new JFrame("登录");
	JLabel         label1 = new JLabel("账号：");
	JLabel         label2 = new JLabel("密码：");
	JTextField     text1  = new JTextField(20);
	JPasswordField text2  = new JPasswordField(20);
	JButton        button = new JButton("登录");

	public LoginPage()
	{
		frame.setLocationRelativeTo(null);//设定窗口出现位置再屏幕中央
		frame.setSize(300, 120);//设定窗口大小
		//添加按钮监听
		button.addActionListener(new LoginButtonListener(frame, text1, text2));
		frame.add(label1);
		frame.add(text1);
		frame.add(label2);
		frame.add(text2);
		frame.add(button);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));//流式布局
		frame.setVisible(true);//窗口可见
		frame.setResizable(false);//调整大小false
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
	}
}
