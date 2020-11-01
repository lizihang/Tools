package com.dm.util;

import com.dm.constant.CommonConstant;
import com.dm.queue.ProgressQueue;

import java.util.HashMap;
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
 * <p>创建日期：2020年10月31日 16:24</p>
 * <p>类全名：com.dm.util.StringUtils</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class StringUtils
{
	/**
	 * 验证json格式是否正确
	 * @param str
	 */
	public static void validJson(String str)
	{
		// TODO
	}

	/**
	 * 将字符串转成map类型
	 * 输入示例：
	 * str: key1:value1,key2:value2
	 * aname:{tipIfOverflow=true,fixedLeft=false,codedata=#FT-CODE.NameTest},aname1:{tipIfOverflow=false,fixedLeft=true,codedata=#FT-CODE.NameTest1}
	 * @param str
	 * @return
	 */
	public static Map<String,Map<String,String>> strToMap(String str)
	{
		if (str == null || str.length() == 0)
		{
			return null;
		}
		Map<String,Map<String,String>> result = new HashMap<>();
		String[] split = str.split(";");
		try
		{
			for (String s : split)
			{
				String key = s.split(":")[0].replace("\"", "");
				String value = s.split(":")[1].replace("\"", "").replace("{", "").replace("}", "");
				Map<String,String> map = new HashMap<>();
				for (String ss : value.split(","))
				{
					map.put(ss.split("=")[0], ss.split("=")[1]);
				}
				result.put(key, map);
			}
		} catch (ArrayIndexOutOfBoundsException e)
		{
			//标签属性字符串有误!
			ProgressQueue.getInstance().putMsg(CommonConstant.ERR00004);
			return null;
		}
		System.out.println(result);
		return result;
	}
}
