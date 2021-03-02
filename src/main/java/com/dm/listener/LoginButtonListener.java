package com.dm.listener;

import com.dm.constant.DmConstants;
import com.dm.service.LoginService;
import com.dm.windows.HomePage;

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
 * <p>创建日期：2020年07月11日 15:08</p>
 * <p>类全名：com.dm.listener.LoginButtonListener</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class LoginButtonListener implements ActionListener
{
	JFrame         frame;
	JTextField     text1;
	JPasswordField text2;
	LoginService   service;

	public LoginButtonListener(JFrame frame, JTextField text1, JPasswordField text2)
	{
		this.frame = frame;
		this.text1 = text1;
		this.text2 = text2;
		service = new LoginService();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String username = text1.getText();
		String password = new String(text2.getPassword());
		if (username.length() == 0 || password.length() == 0)
		{
			//ERR:请输入用户名或密码!
			JOptionPane.showMessageDialog(null, DmConstants.ERR00000);
		}
		boolean isLogin = service.login(username, password);
		if (isLogin)
		{
			new HomePage();
			frame.dispose();
		} else
		{
			JOptionPane.showMessageDialog(null, DmConstants.ERR00001);
		}
	}
}
