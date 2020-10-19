package com.dm.strategy.impl;

import com.dm.data.DomResData;
import com.dm.queue.ProgressQueue;
import com.dm.strategy.TitleStrategy;
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
 * <p>创建日期：2020年06月25日 13:55</p>
 * <p>类全名：com.dm.strategy.impl.CTitleStrategy</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class CTitleStrategy implements TitleStrategy
{
	@Override
	public void modifyTitle(Element element)
	{
		Map<String,String> fColMap = DomResData.getInstance().getfColMap();
		Attribute attr = element.attribute("title");
		if (attr == null)
		{
			String msg = String.format("---<%s name=%s>标签没有title属性！父标签<%s name=%s>", element.getName(), element.attributeValue("name"), element.getParent().getName(), element.getParent().attributeValue("name"));
			ProgressQueue.getInstance().putMsg(msg);
			System.out.println(msg);
			return;
		}
		String oldValue = DomUtil.getAttrValue(attr);
		if ("NoValue".equals(oldValue))
		{
			String msg = String.format("---<%s name=%s>标签的title未设置中文值！", element.getName(), element.attributeValue("name"));
			ProgressQueue.getInstance().putMsg(msg);
			System.out.println(msg);
		}
		String columnName = element.attributeValue("name");
		//固定列要判断字段名和title都一致的时候才替换
		if (fColMap.containsKey(columnName) && oldValue.equals(fColMap.get(columnName)))
		{
			attr.setValue("${RES.$." + columnName + "?" + oldValue + "}");
		} else
		{
			// 2020-6-30 修改不替换res.c只保留中文
			attr.setValue(oldValue);
			// attr.setValue("${RES.C?" + oldValue + "}");
		}
		// 特殊处理 fromsys-系统下发
		if (columnName.contains("fromsys#"))
		{
			columnName = columnName.substring(0, columnName.indexOf("#"));
			attr.setValue("${RES.$." + columnName + "?" + oldValue + "}");
		}
		// 特殊处理 fromfile-文件
		if (columnName.contains("fromfile#"))
		{
			columnName = columnName.substring(0, columnName.indexOf("#"));
			attr.setValue("${RES.$." + columnName + "?" + oldValue + "}");
		}
		// 特殊处理 cuicode-商户，商户编码，有的中文是商户，固定列中是商户编码，所以有的匹配不上
		if (columnName.equals("cuicode"))
		{
			attr.setValue("${RES.$." + columnName + "?商户编码}");
		}
	}
}
