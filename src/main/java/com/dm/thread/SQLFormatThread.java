package com.dm.thread;

import com.dm.constant.DmConstants;

import javax.swing.*;
/**
 * <p>标题：</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2021年03月20日 22:45</p>
 * <p>类全名：com.dm.thread.SQLFormatThread</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class SQLFormatThread extends Thread
{
	private String     sql;
	private JTextArea  area;
	private JTextField text;

	public SQLFormatThread(String sql, JTextArea area, JTextField text)
	{
		this.sql = sql;
		this.area = area;
		this.text = text;
	}

	@Override
	public void run()
	{
		String _sql = getSQLFromString(sql);
		String line = formatSQLToLine(_sql);
		String page = formatSQLToPage(_sql);
		text.setText(line);
		area.setText(page);
	}

	/**
	 * 从字符串中提取SQL语句
	 * @param str 需要被提取语句的字符串
	 * @return 提取的SQL语句
	 */
	private String getSQLFromString(String str)
	{
		str = str.trim();
		String sql = "";
		int startIndex;
		int endIndex;
		int count = 0;
		while (!str.isEmpty())
		{
			startIndex = str.indexOf("\"");
			endIndex = str.indexOf("\"", str.indexOf("\"") + 1);
			//分开写为了看是那个为-1而结束的
			if (startIndex == -1)
			{
				if (count == 0)
				{
					sql = str;
				}
				//				System.out.println("startIndex=" + startIndex);
				break;
			}
			if (endIndex == -1)
			{
				//				System.out.println("endIndex=" + endIndex);
				break;
			}
			sql = sql + " " + str.substring(startIndex + 1, endIndex);
			str = str.substring(endIndex + 1);
			count++;
		}
		return sql;
	}

	/**
	 * 将SQL语句格式化为标准单行格式
	 * @param sql 需要被格式化的SQL
	 * @return 格式化后的SQL
	 */
	private String formatSQLToLine(String sql)
	{
		//去掉前后的空格,并将SQL格式化为标准单行格式,转换为小写
		sql = sql.trim().replaceAll(" ,", ",").replaceAll(",", ", ").replaceAll("\\s+", " ").toLowerCase();
		return sql;
	}

	/**
	 * 将SQL语句格式化为方便浏览的格式
	 * @param sql
	 * @return
	 */
	private String formatSQLToPage(String sql)
	{
		int flag = 0;
		int leftNum = 0;
		int rightNum = 0;
		String keyWords = DmConstants.KEYWORDS;
		String formatSQL = "";
		//将SQL格式化
		sql = formatSQLToLine(sql);
		sql = sql.replaceAll(" ", "| ").replaceAll(",\\|", ",\\$").replaceAll("\\|", " ").replaceAll("\\s+", " ");
		sql = sql.replaceAll("select", "select\\$").replaceAll("from", "\\$from\\$").replaceAll("where", "\\$where\\$").replaceAll("order by", "\\$order by\\$").replaceAll("group by", "\\$group by\\$").replaceAll("union", "\\$union\\$");
		//根据分隔符将SQL分割成字符串数组
		String[] sqls = sql.split("\\$");
		//循环遍历数组，根据关键字调整格式
		for (String s : sqls)
		{
			s = s.trim();
			leftNum += appearNum("(", s);
			rightNum += appearNum(")", s);
			//如果是关键字select，则缩进标记加一
			if (s.contains("select"))
			{
				flag = flag + 1;
			}
			//如果是union，或者右括号多余左括号的个数，则缩进标记减一
			if (s.contains("union"))
			{
				flag = flag - 1;
			}
			//根据关键字，将字符串前加对应缩进标记个"\t"
			if (keyWords.contains(s))
			{
				for (int i = 0; i < flag - 1; i++)
				{
					s = "\t" + s;
				}
			} else
			{
				for (int i = 0; i < flag; i++)
				{
					s = "\t" + s;
				}
			}
			if (leftNum != 0)
			{
				if ((rightNum - leftNum == 0) && (appearNum("(", s) - appearNum(")", s) != 0))
				{
					flag = flag - 1;
					leftNum = 0;
					rightNum = 0;
				}
			}
			//将处理后的字符串拼接成一个完整的字符串
			formatSQL += s + "\r\n";
			// System.out.println(flag + "||" + s);
		}
		return formatSQL;
	}

	/**
	 * 计算一个字符串或字符在另一个字符串中出现的次数
	 * @param findStr 指定的字符或者字符串
	 * @param srcStr 被查找的字符串
	 * @return
	 */
	private static int appearNum(String findStr, String srcStr)
	{
		int count = 0;
		int index = 0;
		while ((index = srcStr.indexOf(findStr, index)) != -1)
		{
			index = index + findStr.length();
			count++;
		}
		return count;
	}
}
