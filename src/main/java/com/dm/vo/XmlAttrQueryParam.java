package com.dm.vo;

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
 * <p>创建日期：2021年03月03日 23:07</p>
 * <p>类全名：com.dm.vo.XmlAttrQueryParam</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class XmlAttrQueryParam extends QueryParam
{
	private boolean                     readRoot;
	private List<XmlAttrQueryCondition> conditions;

	public boolean isReadRoot()
	{
		return readRoot;
	}

	public void setReadRoot(boolean readRoot)
	{
		this.readRoot = readRoot;
	}

	public List<XmlAttrQueryCondition> getConditions()
	{
		return conditions;
	}

	public void setConditions(List<XmlAttrQueryCondition> conditions)
	{
		this.conditions = conditions;
	}
}
