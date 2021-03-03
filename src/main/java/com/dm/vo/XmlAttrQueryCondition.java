package com.dm.vo;

/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2021年03月03日 23:08</p>
 * <p>类全名：com.dm.vo.XmlAttrQueryCondition</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class XmlAttrQueryCondition
{
	// 标签名
	private String tagName;
	// 属性名
	private String attrName;
	// 运算符
	private String op;
	// 属性值
	private String attrValue;

	public String getTagName()
	{
		return tagName;
	}

	public void setTagName(String tagName)
	{
		this.tagName = tagName;
	}

	public String getAttrName()
	{
		return attrName;
	}

	public void setAttrName(String attrName)
	{
		this.attrName = attrName;
	}

	public String getOp()
	{
		return op;
	}

	public void setOp(String op)
	{
		this.op = op;
	}

	public String getAttrValue()
	{
		return attrValue;
	}

	public void setAttrValue(String attrValue)
	{
		this.attrValue = attrValue;
	}
}
