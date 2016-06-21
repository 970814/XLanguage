package test;

import javax.swing.*;

import moon.compile.util.analyst.constant.KeyWord;
import priorityArray.PriorityArray;

import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Administrator on 2016/5/13.
 */
public class PriorityPanel extends JFrame {
	public PriorityPanel() {

		Vector<String[]> strs = KeyWord.STRINGS;
		String[] strings = new String[strs.size()];
		for (int i = 0, size = strings.length; i < size; i++) {
			String[] s = strs.get(i);
			strings[i] = Arrays.toString(s);
		}
		String[][] data = PriorityArray.operatorPriority();
		data[0] = strings;
		JTable table = new JTable(data, strings);
		table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());
		table.setPreferredScrollableViewportSize(new Dimension(1920, 1000));
		add(new JScrollPane(table));
	}
}
