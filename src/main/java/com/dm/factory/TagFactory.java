package com.dm.factory;

import com.dm.constant.DmConstants;
import com.dm.strategy.TagStrategy;
import com.dm.strategy.impl.*;

import java.util.HashMap;
import java.util.Map;
/**
 * <p>标题：标签策略工厂</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：策略模式的静态工厂
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2020年06月25日 15:30</p>
 * <p>类全名：com.dm.factory.TitleFactory</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class TagFactory
{
	private static Map<String,TagStrategy> strategyMap = new HashMap<>();

	static
	{
		strategyMap.put(DmConstants.C_NAME, new CTagStrategy());
		// btn和attr用同一个strategy
		strategyMap.put(DmConstants.BTN_NAME, new BtnTagStrategy());
		strategyMap.put(DmConstants.O_NAME, new OTagStrategy());
		strategyMap.put(DmConstants.GRID_NAME, new GridTagStrategy());
		strategyMap.put(DmConstants.RECORD_NAME, new RecordTagStrategy());
	}

	private TagFactory()
	{
	}

	private static final TagStrategy EMPTY = new EmptyTagStrategy();

	//获取
	public static TagStrategy getStrategy(String state)
	{
		TagStrategy result = strategyMap.get(state);
		return result == null ? EMPTY : result;
	}

	//将对象注册到这里
	public static void registerStrategy(String state, TagStrategy o)
	{
		strategyMap.put(state, o);
	}
}
