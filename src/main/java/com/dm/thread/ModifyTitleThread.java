package com.dm.thread;

import com.dm.constant.DmConstants;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
		List<String> errFileList = new ArrayList<>();
		// 1.获取目录下所有xml文件
		SAXReader saxReader = new SAXReader();
		ProgressQueue.getInstance().putMsg(String.format(DmConstants.START_MSG, "修改文件title"));
		for (String fPath : resData.getFiles())
		{
			ProgressQueue.getInstance().putMsg("--------------------------------------------------");
			ProgressQueue.getInstance().putMsg("---开始修改文件：" + fPath);
			System.out.println("--------------------------------------------------");
			System.out.println("---开始修改文件：" + fPath);
			try
			{
				Document doc = saxReader.read(fPath);
				// 获取根节点，增加all_c_columns属性
				Element root = doc.getRootElement();
				// TODO
				// readRootAttr(root);
				// TODO 修改根节点，是否提成单独的功能
				// modifyRoot(root);
				// 递归修改标签title
				modifyTitle(root);
				String newFile = fPath.replace(".xml", "-new.xml");
				XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(new File(newFile)));
				xmlWriter.write(doc);
				xmlWriter.flush();
				xmlWriter.close();
			} catch (Exception e)
			{
				errFileList.add(fPath + ";" + e.getMessage());
				e.printStackTrace();
			}
			ProgressQueue.getInstance().putMsg("---文件：" + fPath + "修改完成");
			ProgressQueue.getInstance().putMsg("--------------------------------------------------");
			System.out.println("---文件：" + fPath + "修改完成");
			System.out.println("--------------------------------------------------");
		}
		ProgressQueue.getInstance().putMsg(errFileStr(errFileList));
		System.out.println(errFileStr(errFileList));
		ProgressQueue.getInstance().putMsg(String.format(DmConstants.END_MSG, "修改文件title"));
	}

	/**
	 * 修改根节点属性
	 * @param root 根节点
	 */
	private void modifyRoot(Element root)
	{
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
		Attribute options0 = root.attribute("options0");
		if (options0 == null)
		{
			root.addAttribute("options0", "32");
		}
	}

	private void readRootAttr(Element root)
	{
		Attribute options0 = root.attribute("options0");
		if (options0 == null)
		{
			String msg = "该文件options0属性为空！";
			throw new RuntimeException(msg);
		} else
		{
			String value = options0.getValue();
			if (!value.equals("32"))
			{
				String msg = "该文件options0属性值不等于32，当前值为：" + value;
				throw new RuntimeException(msg);
			}
		}
	}

	/**
	 * 修改title
	 * @param rootElement
	 */
	private void modifyTitle(Element rootElement)
	{
		Iterator<Element> it = rootElement.elementIterator();
		while (it.hasNext())
		{
			Element element = it.next();
			String elementName = element.getName();
			switch (elementName)
			{
			case DmConstants.C_NAME://c标签
				TagFactory.getStrategy(DmConstants.C_NAME).modifyTitle(element);
				break;
			case DmConstants.BTN_NAME://按钮
			case DmConstants.ATTR_NAME://按钮
				TagFactory.getStrategy(DmConstants.BTN_NAME).modifyTitle(element);
				break;
			case DmConstants.O_NAME://Operate标签
				TagFactory.getStrategy(DmConstants.O_NAME).modifyTitle(element);
				break;
			case DmConstants.GRID_NAME://grid表
			case DmConstants.DIALOG_NAME://查询面板
			case DmConstants.TOOLBAR_NAME://工具条
			case DmConstants.RECORD_NAME://record表
				TagFactory.getStrategy(DmConstants.GRID_NAME).modifyTitle(element);
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

	/**
	 * 输出错误字符串
	 * @param errFileList
	 * @return
	 */
	private String errFileStr(List<String> errFileList)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("错误文件数量：").append(errFileList.size()).append(";\r\n");
		sb.append("错误文件列表：").append("\r\n");
		for (String str : errFileList)
		{
			sb.append(str).append("\r\n");
		}
		return sb.toString();
	}
}
