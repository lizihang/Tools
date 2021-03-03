package com.dm.thread;

import com.dm.constant.DmConstants;
import com.dm.data.DomResData;
import com.dm.queue.ProgressQueue;
import com.dm.vo.XmlAttrQueryCondition;
import com.dm.vo.XmlAttrQueryParam;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
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
 * <p>创建日期：2021年03月03日 23:00</p>
 * <p>类全名：com.dm.thread.XmlAttrQueryThread</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class XmlAttrQueryThread extends Thread
{
	private String            filePath;
	private XmlAttrQueryParam queryParam;

	public XmlAttrQueryThread(String filePath, XmlAttrQueryParam queryParam)
	{
		this.filePath = filePath;
		this.queryParam = queryParam;
	}

	@Override
	public void run()
	{
		// 0.初始化资源文件等数据
		DomResData resData = DomResData.getInstance();
		resData.init(null, filePath);
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
				// 获取根节点
				Element root = doc.getRootElement();
				// TODO
				readRootAttr(root);
				// 读取
				readEleAttr(root);
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
		ProgressQueue.getInstance().putMsg(String.format(DmConstants.END_MSG, "修改文件title"));
	}

	private void readEleAttr(Element root)
	{

	}

	private boolean readRootAttr(Element root)
	{
		// TODO result每次循环要result和结果取或
		boolean result = true;
		List<XmlAttrQueryCondition> conditions = queryParam.getConditions();
		for (XmlAttrQueryCondition condition : conditions)
		{
			Attribute attr = root.attribute(condition.getAttrName());
			if (attr == null)
			{
				result = result | condition.getAttrValue() == null;
			} else
			{
				if (condition.getOp().equals("="))
				{
					result = attr.getValue().equals(condition.getAttrValue());
				} else
				{
					Integer attrValue = Integer.parseInt(attr.getValue());
					Integer paramValue = Integer.parseInt(condition.getAttrValue());
					if (condition.getOp().equals(">"))
					{
						result = attrValue.compareTo(paramValue) > 0;
					} else if (condition.getOp().equals("<"))
					{
						result = attrValue.compareTo(paramValue) < 0;
					} else
					{
						result = false;
					}
				}
			}
		}
		return result;
	}
}
