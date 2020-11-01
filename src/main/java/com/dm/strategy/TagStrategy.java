package com.dm.strategy;

import com.dm.queue.ProgressQueue;
import com.dm.util.DomUtil;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.Map;
import java.util.Map.Entry;
/**
 * <p>标题：标签处理策略接口</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：策略模式，用于优化多if-else代码
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月25日 13:53</p>
 * <p>类全名：com.dm.strategy.TitleStrategy</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public interface TagStrategy
{
	void modifyTitle(Element element);

	default void modifyProp(Element element, Map<String,Map<String,String>> props)
	{
		String namePrp = element.attributeValue("name");
		if (props.containsKey(namePrp))
		{
			for (Entry<String,String> entry : props.get(namePrp).entrySet())
			{
				Attribute attr = element.attribute(entry.getKey());
				String key = entry.getKey();
				String value = entry.getValue();
				if (attr == null)
				{
					// 属性为空，设置属性
					element.addAttribute(key, value);
					// 输出信息
					String msg = String.format("---<%s name=%s>标签没有%s属性，已设置为：%s", element.getName(), namePrp, key, value);
					ProgressQueue.getInstance().putMsg(msg);
					System.out.println(msg);
				} else
				{
					// 属性不为空，更新属性
					attr.setValue(value);
					// 输出信息
					String oldValue = DomUtil.getAttrValue(attr);
					String msg = String.format("---<%s name=%s>标签更新%s属性，新值为：%s，旧值为：%s", element.getName(), namePrp, key, value, oldValue);
					ProgressQueue.getInstance().putMsg(msg);
					System.out.println(msg);
				}
			}
		}
	}
}
