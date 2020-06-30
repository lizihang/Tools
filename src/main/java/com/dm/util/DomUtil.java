package com.dm.util;

import com.dm.queue.ProgressQueue;
import org.dom4j.Attribute;
import org.dom4j.Element;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月25日 13:57</p>
 * <p>类全名：com.dm.util.DomUtil</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class DomUtil
{
	/**
	 * 检查element的属性是否为空，空返回true，不为空返回false
	 * @param element element对象
	 * @param attrName 属性名
	 * @return 空返回true，不为空返回false
	 */
	public static boolean checkAttrIsNull(Element element, String attrName)
	{
		if (element.attribute(attrName) == null)
		{
			String msg = String.format("name=%s的<%s>标签没有%s属性！父标签类型<%s>，name=%s", element.attributeValue("name"), element.getName(), attrName, element.getParent().getName(), element.getParent().attributeValue("name"));
			ProgressQueue.getInstance().putMsg(msg);
			System.out.println(msg);
			return true;
		}
		return false;
	}

	/**
	 * 获取attr属性值，目前只用来获取title的值
	 * @param attr attr
	 * @return 值
	 */
	public static String getAttrValue(Attribute attr)
	{
		String oldValue = attr.getValue();
		//判断原来是否有宏，有的话看是否配了？后的中文，并截取，不存在返回NoValue
		if (oldValue.contains("{"))
		{
			oldValue = oldValue.indexOf("?") > 0 ? oldValue.substring(oldValue.indexOf("?") + 1, oldValue.length() - 1) : "NoValue";
		}
		return oldValue;
	}
}
