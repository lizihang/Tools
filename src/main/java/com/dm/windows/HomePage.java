package com.dm.windows;

import com.dm.constant.DmConstants;
import com.dm.factory.PageFactory;
import com.dm.listener.OpenTabListener;

import javax.swing.*;
import java.awt.*;
/**
 * <p>标题：主页</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年04月06日 15:03</p>
 * <p>类全名：com.dm.windows.HomePage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class HomePage
{
	JFrame frame = new JFrame("DM小工具");

	public HomePage()
	{
		// 取得屏幕的高度和宽度
		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		// 设定窗口大小
		frame.setSize(DmConstants.WIDTH_WINDOW, DmConstants.HEIGHT_WINDOW);
		// 设定窗口出现位置居中
		frame.setLocation(new Point((int) (lx - DmConstants.WIDTH_WINDOW) / 2, (int) (ly - DmConstants.HEIGHT_WINDOW) / 2));
		// 选项卡布局
		JTabbedPane tabPane = createJTabbedPane();
		// 菜单
		frame.setJMenuBar(createMenu(tabPane));
		// 设置布局
		frame.setContentPane(tabPane);
		tabPane.add("xml属性查询", PageFactory.getPage("xml属性查询"));
		// 窗口可见
		frame.setVisible(true);
		// 禁止调大小
		frame.setResizable(false);
		// 使能关闭窗口，结束程序
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JTabbedPane createJTabbedPane()
	{
		return new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private JMenuBar createMenu(JTabbedPane tabPane)
	{
		JMenuBar menuBar = new JMenuBar();
		/** 一级菜单 */
		JMenu fileMenu = new JMenu("文件");
		JMenu xmlMenu = new JMenu("xml修改");
		JMenu aboutMenu = new JMenu("关于");
		menuBar.add(fileMenu);
		menuBar.add(xmlMenu);
		menuBar.add(aboutMenu);
		/** 二级菜单 */
		JMenuItem fileCopy = new JMenuItem("文件复制");
		JMenuItem fileDelete = new JMenuItem("文件删除");
		JMenuItem fileRename = new JMenuItem("文件重命名");
		JMenuItem fileDownload = new JMenuItem("文件下载");
		// 二级菜单添加到一级菜单
		fileMenu.add(fileCopy);
		fileMenu.add(fileDelete);
		fileMenu.add(fileRename);
		fileMenu.add(fileDownload);
		// 监听
		fileCopy.addActionListener(new OpenTabListener(tabPane, fileCopy));
		fileDelete.addActionListener(new OpenTabListener(tabPane, fileDelete));
		fileRename.addActionListener(new OpenTabListener(tabPane, fileRename));
		fileDownload.addActionListener(new OpenTabListener(tabPane, fileDownload));
		/** 二级菜单 */
		JMenuItem xmlQuery = new JMenuItem("xml属性查询");
		JMenuItem xmlModifyTitle = new JMenuItem("修改title");
		JMenuItem xmlModifyProp = new JMenuItem("修改属性");
		// 二级菜单添加到一级菜单
		xmlMenu.add(xmlQuery);
		xmlMenu.add(xmlModifyTitle);
		xmlMenu.add(xmlModifyProp);
		// 监听
		xmlQuery.addActionListener(new OpenTabListener(tabPane, xmlQuery));
		xmlModifyTitle.addActionListener(new OpenTabListener(tabPane, xmlModifyTitle));
		xmlModifyProp.addActionListener(new OpenTabListener(tabPane, xmlModifyProp));
		/** 二级菜单 */
		JMenuItem about = new JMenuItem("更新日志");
		// 二级菜单添加到一级菜单
		aboutMenu.add(about);
		// 监听
		about.addActionListener(new OpenTabListener(tabPane, about));
		// 返回
		return menuBar;
	}
}
