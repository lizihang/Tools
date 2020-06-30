package com.dm.strategy;

import org.dom4j.Element;
/**
 * <p>标题：</p>
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
public interface TitleStrategy
{
	void modifyTitle(Element element);
}
