package com.dm.thread;

import com.dm.constant.CommonConstant;
import com.dm.data.DomResData;
import com.dm.factory.TagFactory;
import com.dm.queue.ProgressQueue;
import com.dm.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年10月31日 15:26</p>
 * <p>类全名：com.dm.thread.PropSetThread</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class ModifyPropThread extends Thread
{
	// 文件路径
	String filePath;
	// 标签名
	String tagName;
	// 需要设置的属性，json格式
	String props;

	public ModifyPropThread(String filePath, String tagName, String props)
	{
		this.filePath = filePath;
		this.tagName = tagName;
		this.props = props;
	}

	@Override
	public void run()
	{
		// 0.初始化资源文件等数据
		DomResData resData = DomResData.getInstance();
		resData.init(null, filePath);
		// TODO 验证props格式，并转成map
		Map<String,Map<String,String>> params = StringUtils.jsonToMap(props);
		// 1.获取目录下所有xml文件
		SAXReader saxReader = new SAXReader();
		try
		{
			ProgressQueue.getInstance().putMsg(String.format(CommonConstant.START_MSG, "批量修改标签属性"));
			for (String fPath : resData.getFiles())
			{
				ProgressQueue.getInstance().putMsg("--------------------------------------------------");
				ProgressQueue.getInstance().putMsg("---开始修改文件：" + fPath);
				System.out.println("--------------------------------------------------");
				System.out.println("---开始修改文件：" + fPath);
				Document doc = saxReader.read(fPath);
				// 获取根节点
				Element root = doc.getRootElement();
				// 递归修改标签属性
				modifyProp(root, params);
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
			ProgressQueue.getInstance().putMsg(String.format(CommonConstant.END_MSG, "批量修改标签属性"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void modifyProp(Element element, Map<String,Map<String,String>> params)
	{
		String elementName = element.getName();
		if (elementName.equals(tagName))
		{
			TagFactory.getStrategy(tagName).modifyProp(element, params);
		}
		Iterator<Element> it = element.elementIterator();
		while (it.hasNext())
		{
			modifyProp(it.next(), params);
		}
	}
}
