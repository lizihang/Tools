package com.dm.windows;

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
	JFrame      frame   = new JFrame("文件处理小工具");
	JTabbedPane tabPane = new JTabbedPane();//选项卡布局
	Container   page1   = new FileCopyPage();//布局1
	Container   page2   = new FileDeletePage();//布局2
	//Container   page3   = new FileDownloadPage();//布局3
	Container   page4   = new FileRenamePage();//布局4
	Container   page5   = new ModifyTitlePage();//布局5
	Container   page6   = new ModifyPropPage();//布局6

	public HomePage()
	{
		//下面两行是取得屏幕的高度和宽度
		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setLocation(new Point((int) (lx / 2) - 350, (int) (ly / 2) - 250));//设定窗口出现位置
		frame.setSize(400, 440);//设定窗口大小
		frame.setContentPane(tabPane);//设置布局
		// tabPane.add("文件复制", page1);
		tabPane.add("文件删除", page2);
		//tabPane.add("文件下载", page3);
		tabPane.add("文件重命名", page4);
		tabPane.add("修改文件title", page5);
		tabPane.add("修改标签属性", page6);
		frame.setVisible(true);//窗口可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
	}
}
