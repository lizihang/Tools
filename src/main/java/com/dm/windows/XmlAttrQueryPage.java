package com.dm.windows;

import com.dm.constant.DmConstants;
import com.dm.listener.ChooseButtonListener;
import com.dm.thread.WriteMsgThread;
import com.dm.thread.XmlAttrQueryThread;
import com.dm.vo.XmlAttrQueryCondition;
import com.dm.vo.XmlAttrQueryParam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
/**
 * <p>标题：xml文件属性查询页</p>
 * <p>功能：</p>
 * <pre>
 * 其他说明：
 * </pre>
 * <p>作者：lizh</p>
 * <p>审核：</p>
 * <p>重构：</p>
 * <p>创建日期：2021年03月02日 17:36</p>
 * <p>类全名：com.dm.windows.XmlAttrQueryPage</p>
 * 查看帮助：<a href="" target="_blank"></a>
 */
public class XmlAttrQueryPage extends Container
{
	private static final long serialVersionUID = 1986288621554901346L;
	JLabel            label1     = new JLabel("选择文件目录");
	JLabel            label2     = new JLabel("标签名称");
	JTextField        text1      = new JTextField();
	JTextArea         area       = new JTextArea(3, 20);//构造一个文本域
	JButton           button1    = new JButton("选择");
	JButton           execute    = new JButton("查询");
	JButton           reset      = new JButton("重置");
	JFileChooser      jfc        = new JFileChooser();//文件选择器
	// 标签下拉框
	JComboBox<String> tagBox1    = new JComboBox<>();
	JComboBox<String> tagBox2    = new JComboBox<>();
	JComboBox<String> tagBox3    = new JComboBox<>();
	// 属性名
	JTextField        attrName1  = new JTextField();
	JTextField        attrName2  = new JTextField();
	JTextField        attrName3  = new JTextField();
	// 运算符下拉框
	JComboBox<String> opBox1     = new JComboBox<>();
	JComboBox<String> opBox2     = new JComboBox<>();
	JComboBox<String> opBox3     = new JComboBox<>();
	// 属性值
	JTextField        attrValue1 = new JTextField();
	JTextField        attrValue2 = new JTextField();
	JTextField        attrValue3 = new JTextField();
	// 多选框
	JCheckBox         checkBox   = new JCheckBox("查询根节点");

	public XmlAttrQueryPage()
	{
		// jfc.setCurrentDirectory(new File("D:/snsoft90/sn_ft/ft-sna/ft-sna/sna-ui/src/main/resources/cfg/ui/res/FT-SNA"));
		jfc.setCurrentDirectory(new File("D:/snsoft90/sn_ft"));
		label1.setBounds(10, 10, DmConstants.WIDTH_LABEL, DmConstants.HEIGHT_COMMON);
		text1.setBounds(10 + DmConstants.WIDTH_LABEL, 10, DmConstants.WIDTH_TEXT + 100, DmConstants.HEIGHT_COMMON);
		text1.setEditable(false);
		button1.setBounds(420, 10, DmConstants.WIDTH_BUTTON, DmConstants.HEIGHT_COMMON);
		// 查询条件1
		tagBox1.setBounds(10, 40, DmConstants.WIDTH_BOX, DmConstants.HEIGHT_COMMON);
		tagBoxAddItem(tagBox1);
		attrName1.setBounds(10 + 100, 40, DmConstants.WIDTH_LABEL, DmConstants.HEIGHT_COMMON);
		opBox1.setBounds(10 + 200, 40, DmConstants.WIDTH_BOX, DmConstants.HEIGHT_COMMON);
		opBoxAddItem(opBox1);
		attrValue1.setBounds(10 + 300, 40, DmConstants.WIDTH_LABEL, DmConstants.HEIGHT_COMMON);
		// 查询条件2
		tagBox2.setBounds(10, 70, DmConstants.WIDTH_BOX, DmConstants.HEIGHT_COMMON);
		tagBoxAddItem(tagBox2);
		attrName2.setBounds(10 + 100, 70, DmConstants.WIDTH_LABEL, DmConstants.HEIGHT_COMMON);
		opBox2.setBounds(10 + 200, 70, DmConstants.WIDTH_BOX, DmConstants.HEIGHT_COMMON);
		opBoxAddItem(opBox2);
		attrValue2.setBounds(10 + 300, 70, DmConstants.WIDTH_LABEL, DmConstants.HEIGHT_COMMON);
		// 查询条件3
		tagBox3.setBounds(10, 100, DmConstants.WIDTH_BOX, DmConstants.HEIGHT_COMMON);
		tagBoxAddItem(tagBox3);
		attrName3.setBounds(10 + 100, 100, DmConstants.WIDTH_LABEL, DmConstants.HEIGHT_COMMON);
		opBox3.setBounds(10 + 200, 100, DmConstants.WIDTH_BOX, DmConstants.HEIGHT_COMMON);
		opBoxAddItem(opBox3);
		attrValue3.setBounds(10 + 300, 100, DmConstants.WIDTH_LABEL, DmConstants.HEIGHT_COMMON);
		// 按钮
		reset.setBounds(420, 70, DmConstants.WIDTH_BUTTON, DmConstants.HEIGHT_COMMON);
		execute.setBounds(420, 100, DmConstants.WIDTH_BUTTON, DmConstants.HEIGHT_COMMON);
		//
		checkBox.setBounds(420, 40, 100, DmConstants.HEIGHT_COMMON);
		// 结果
		area.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
		area.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 130, DmConstants.WIDTH_AREA, DmConstants.HEIGHT_AREA);
		// 选择路径按钮的监听
		button1.addActionListener(new ChooseButtonListener(jfc, text1, JFileChooser.FILES_AND_DIRECTORIES));
		// 多选框监听
		checkBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tagBox1.setSelectedIndex(0);
				tagBox2.setSelectedIndex(0);
				tagBox3.setSelectedIndex(0);
				tagBox1.setEnabled(!checkBox.isSelected());
				tagBox2.setEnabled(!checkBox.isSelected());
				tagBox3.setEnabled(!checkBox.isSelected());
			}
		});
		// 重置按钮监听
		reset.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				text1.setText(null);
				tagBox1.setSelectedIndex(0);
				tagBox2.setSelectedIndex(0);
				tagBox3.setSelectedIndex(0);
				attrName1.setText(null);
				attrName2.setText(null);
				attrName3.setText(null);
				opBox1.setSelectedIndex(0);
				opBox2.setSelectedIndex(0);
				opBox3.setSelectedIndex(0);
				attrValue1.setText(null);
				attrValue2.setText(null);
				attrValue3.setText(null);
				checkBox.setSelected(false);
			}
		});
		// 查询按钮的监听
		execute.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String filePath = text1.getText();
				if (filePath.length() == 0)
				{
					//ERR:请选择目录!
					JOptionPane.showMessageDialog(null, DmConstants.ERR00002);
					return;
				}
				// filePath += "\\src\\main\\resources\\cfg\\ui\\res";
				new WriteMsgThread(area).start();
				XmlAttrQueryParam params = new XmlAttrQueryParam();
				ArrayList<XmlAttrQueryCondition> conditions = new ArrayList<>();
				//
				if (attrName1.getText().trim().length() > 0)
				{
					XmlAttrQueryCondition condition = new XmlAttrQueryCondition();
					condition.setTagName(tagBox1.getSelectedItem().toString());
					condition.setAttrName(attrName1.getText());
					condition.setOp(opBox1.getSelectedItem().toString());
					condition.setAttrValue(attrValue1.getText());
					conditions.add(condition);
				}
				if (attrName2.getText().trim().length() > 0)
				{
					XmlAttrQueryCondition condition = new XmlAttrQueryCondition();
					condition.setTagName(tagBox2.getSelectedItem().toString());
					condition.setAttrName(attrName2.getText());
					condition.setOp(opBox2.getSelectedItem().toString());
					condition.setAttrValue(attrValue2.getText());
					conditions.add(condition);
				}
				if (attrName3.getText().trim().length() > 0)
				{
					XmlAttrQueryCondition condition = new XmlAttrQueryCondition();
					condition.setTagName(tagBox3.getSelectedItem().toString());
					condition.setAttrName(attrName3.getText());
					condition.setOp(opBox3.getSelectedItem().toString());
					condition.setAttrValue(attrValue3.getText());
					conditions.add(condition);
				}
				if (conditions.size() == 0)
				{
					//ERR:至少输入一条查询条件!
					JOptionPane.showMessageDialog(null, DmConstants.ERR00005);
					return;
				}
				params.setConditions(conditions);
				params.setReadRoot(checkBox.isSelected());
				new XmlAttrQueryThread(filePath, params).start();
			}
		});
		//文件路径
		this.add(label1);
		this.add(text1);
		this.add(button1);
		// 查询条件
		this.add(tagBox1);
		this.add(tagBox2);
		this.add(tagBox3);
		this.add(attrName1);
		this.add(attrName2);
		this.add(attrName3);
		this.add(opBox1);
		this.add(opBox2);
		this.add(opBox3);
		this.add(attrValue1);
		this.add(attrValue2);
		this.add(attrValue3);
		this.add(checkBox);
		// 按钮
		this.add(reset);
		this.add(execute);
		// 结果
		this.add(scrollPane);
		this.add(jfc);
	}

	private void tagBoxAddItem(JComboBox<String> tagBox)
	{
		tagBox.addItem("");
		tagBox.addItem("c");
		tagBox.addItem("ToolbarBtn");
		tagBox.addItem("attr");
		tagBox.addItem("Operate");
		tagBox.addItem("GridTable");
		tagBox.addItem("DialogPane");
		tagBox.addItem("Toolbar");
		tagBox.addItem("RecordTable");
	}

	private void opBoxAddItem(JComboBox<String> opBox)
	{
		opBox.addItem("==");
		opBox.addItem("!=");
		opBox.addItem(">=");
		opBox.addItem("<=");
	}
}
