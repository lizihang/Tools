package com.dm.strategy.impl;

import com.dm.queue.ProgressQueue;
import com.dm.strategy.TagStrategy;
import com.dm.util.DomUtil;
import org.dom4j.Attribute;
import org.dom4j.Element;

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
 * <p>创建日期：2020年06月25日 16:03</p>
 * <p>类全名：com.dm.strategy.impl.GridTitleStrategy</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class GridTagStrategy implements TagStrategy
{
	@Override
	public void modifyTitle(Element element)
	{
		Attribute attr = element.attribute("title");
		if (attr == null)
		{
			element.addAttribute("title", "${RES.T}");
			return;
		}
		String oldValue = DomUtil.getAttrValue(attr);
		if ("NoValue".equals(oldValue))
		{
			String msg = String.format("---<%s name=%s>标签的title未设置中文值！", element.getName(), element.attributeValue("name"));
			ProgressQueue.getInstance().putMsg(msg);
			System.out.println(msg);
		}
		attr.setValue("${RES.T?" + oldValue + "}");
	}

	@Override
	public void modifyProp(Element element, Map<String,Map<String,String>> props)
	{
	}
}
