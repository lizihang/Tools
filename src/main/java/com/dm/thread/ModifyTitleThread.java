package com.dm.thread;

import com.dm.constant.CommonConstant;
import com.dm.constant.DomConstant;
import com.dm.data.DomResData;
import com.dm.factory.TagFactory;
import com.dm.queue.ProgressQueue;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月25日 22:05</p>
 * <p>类全名：com.dm.thread.ModifyTitleThread</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class ModifyTitleThread extends Thread
{
	private String resPath;
	private String filePath;

	public ModifyTitleThread(String resPath, String filePath)
	{
		this.resPath = resPath;
		this.filePath = filePath;
	}

	@Override
	public void run()
	{
		// 0.初始化资源文件等数据
		DomResData resData = DomResData.getInstance();
		resData.init(resPath, filePath);
		// 1.获取目录下所有xml文件
		SAXReader saxReader = new SAXReader();
		try
		{
			ProgressQueue.getInstance().putMsg(String.format(CommonConstant.START_MSG, "修改文件title"));
			for (String fPath : resData.getFiles())
			{
				ProgressQueue.getInstance().putMsg("--------------------------------------------------");
				ProgressQueue.getInstance().putMsg("---开始修改文件：" + fPath);
				System.out.println("--------------------------------------------------");
				System.out.println("---开始修改文件：" + fPath);
				Document doc = saxReader.read(fPath);
				// 获取根节点，增加all_c_columns属性
				Element root = doc.getRootElement();
				Attribute acc = root.attribute("all_c_columns");
				if (acc == null)
				{
					root.addAttribute("all_c_columns", "true");
				}
				Attribute title = root.attribute("title");
				if (title == null)
				{
					root.addAttribute("title", "${}");
				} else
				{
					// TODO 暂时不替换，方便看名称
					// title.setValue("${}");
				}
				// 递归修改标签title
				modifyTitle(root);
				String newFile = fPath.replace(".xml", "-new.xml");
				XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(new File(newFile)));
				xmlWriter.write(doc);
				xmlWriter.flush();
				xmlWriter.close();
				ProgressQueue.getInstance().putMsg("---文件：" + fPath + "修改完成");
				ProgressQueue.getInstance().putMsg("--------------------------------------------------");
				System.out.println("---文件：" + fPath + "修改完成");
				System.out.println("--------------------------------------------------");
			}
			ProgressQueue.getInstance().putMsg(String.format(CommonConstant.END_MSG, "修改文件title"));
		} catch (Exception e)
		{
			//System.out.println("modifyFile方法报错，错误信息为："+ Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

	private void modifyTitle(Element rootElement)
	{
		Iterator<Element> it = rootElement.elementIterator();
		while (it.hasNext())
		{
			Element element = it.next();
			String elementName = element.getName();
			switch (elementName)
			{
			case DomConstant.C_NAME://c标签
				TagFactory.getStrategy(DomConstant.C_NAME).modifyTitle(element);
				break;
			case DomConstant.BTN_NAME://按钮
			case DomConstant.ATTR_NAME://按钮
				TagFactory.getStrategy(DomConstant.BTN_NAME).modifyTitle(element);
				break;
			case DomConstant.O_NAME://Operate标签
				TagFactory.getStrategy(DomConstant.O_NAME).modifyTitle(element);
				break;
			case DomConstant.GRID_NAME://grid表
			case DomConstant.DIALOG_NAME://查询面板
			case DomConstant.TOOLBAR_NAME://工具条
			case DomConstant.RECORD_NAME://record表
				TagFactory.getStrategy(DomConstant.GRID_NAME).modifyTitle(element);
				modifyTitle(element);
				break;
			/* 2020-10-15 注释掉，因为和上边的一样，title改成${RES.T?xxx}
			case DomConstant.RECORD_NAME:
				TitleFactory.getStrategy(DomConstant.RECORD_NAME).modifyTitle(element);
				modifyTitle(element);
				break;
			*/
			default://不是以上的，递归调用
				modifyTitle(element);
			}
		}
	}
}
