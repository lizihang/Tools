package com.dm.thread;

import com.dm.constant.DmConstants;
import com.dm.data.DomResData;
import com.dm.queue.ProgressQueue;
import com.dm.util.StringUtils;
import com.dm.vo.XmlAttrQueryCondition;
import com.dm.vo.XmlAttrQueryParam;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
 * <p>创建日期：2021年03月03日 23:00</p>
 * <p>类全名：com.dm.thread.XmlAttrQueryThread</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class XmlAttrQueryThread extends Thread
{
	private String            filePath;
	private XmlAttrQueryParam queryParam;
	private boolean           isMatch = false;

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
		ProgressQueue.getInstance().putMsg(String.format(DmConstants.START_MSG, "查询符合条件xml文件"));
		for (String fPath : resData.getFiles())
		{
			try
			{
				Document doc = saxReader.read(fPath);
				// 获取根节点
				Element root = doc.getRootElement();
				if (queryParam.isReadRoot())
				{
					if (readRootAttr(root))
					{
						ProgressQueue.getInstance().putMsg(fPath);
						System.out.println(fPath);
					}
				} else
				{
					isMatch = false;
					readEleAttr(root);
					if (isMatch)
					{
						ProgressQueue.getInstance().putMsg(fPath);
						System.out.println(fPath);
					}
				}
			} catch (Exception e)
			{
				errFileList.add(fPath + ";" + e.getMessage());
				e.printStackTrace();
			}
		}
		String errMsg = StringUtils.getErrFileStr(errFileList);
		ProgressQueue.getInstance().putMsg(errMsg);
		System.out.println(errMsg);
		ProgressQueue.getInstance().putMsg(String.format(DmConstants.END_MSG, "查询符合条件xml文件"));
	}

	/**
	 * 判断xml是否符合查询条件
	 * @param root
	 * @return
	 */
	private void readEleAttr(Element root)
	{
		Iterator<Element> it = root.elementIterator();
		while (it.hasNext())
		{
			Element element = it.next();
			// 只要有一个符合查询条件的就返回true
			if (checkEleMatch(element))
			{
				isMatch = true;
				break;
			}
			// 递归调用
			readEleAttr(element);
		}
	}

	/**
	 * 判断element是否匹配查询条件
	 * @param element element
	 * @return true:匹配;false:不匹配
	 */
	private boolean checkEleMatch(Element element)
	{
		int matchNum = 0;
		List<XmlAttrQueryCondition> conditions = queryParam.getConditions();
		for (XmlAttrQueryCondition condition : conditions)
		{
			if (condition.getTagName() != null && condition.getTagName().length()!=0)
			{
				// 1.如果查询条件中tagName不为空，则要判断element名称和tagName是否相同，如果相同则判断element属性是否匹配queryParam
				if (element.getName().equals(condition.getTagName()))
				{
					if (checkAttrMatch(element, condition))
					{
						matchNum++;
					}
				}
			} else
			{
				// 2.如果查询条件中tagName为空，则直接判断element属性是否匹配queryParam
				if (checkAttrMatch(element, condition))
				{
					matchNum++;
				}
			}
		}
		return conditions.size() == matchNum;
	}

	/**
	 * 判断根节点是否符合查询条件
	 * @param root
	 * @return
	 */
	private boolean readRootAttr(Element root)
	{
		List<XmlAttrQueryCondition> conditions = queryParam.getConditions();
		for (XmlAttrQueryCondition condition : conditions)
		{
			// 只要有一个属性不匹配，直接返回false
			if (!checkAttrMatch(root, condition))
			{
				return false;
			}
		}
		// 如果都匹配，则返回true
		return true;
	}

	/**
	 * 判断attr是否符合查询条件
	 * @param element element
	 * @param condition condition
	 * @return true:匹配;false:不匹配
	 */
	private boolean checkAttrMatch(Element element, XmlAttrQueryCondition condition)
	{
		Attribute attr = element.attribute(condition.getAttrName());
		if (attr == null)
		{
			return condition.getAttrValue() == null;
		} else
		{
			switch (condition.getOp())
			{
			case "==":
				if (!attr.getValue().equals(condition.getAttrValue()))
				{
					return false;
				}
				break;
			case "!=":
				if (attr.getValue().equals(condition.getAttrValue()))
				{
					return false;
				}
				break;
			case ">=":
			{
				int attrValue = Integer.parseInt(attr.getValue());
				int paramValue = Integer.parseInt(condition.getAttrValue());
				if (attrValue < paramValue)
				{
					return false;
				}
				break;
			}
			case "<=":
			{
				try
				{
					int attrValue = Integer.parseInt(attr.getValue());
					int paramValue = Integer.parseInt(condition.getAttrValue());
					if (attrValue > paramValue)
					{
						return false;
					}
				} catch (NumberFormatException e)
				{
					String msg = String.format("查询条件<%s>数字转换异常！", condition.getAttrName());
					throw new RuntimeException(msg);
				}
				break;
			}
			default:
				return false;
			}
		}
		return true;
	}
}
