package test;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class Test12 extends JFrame {
	public Test12() {
		JTable tbl = new JTable(new String[][] {
				{ "JTable �ﵥԪ�����ݵ���ʾ���� TableCellRenderer��", "Ĭ�ϵ���ʾ����DefaultTableCellRenderer���̳� JLabel ���Բ����������ʾ��",
						"Ҫ������ʾӦ�ü̳� JTextArea���ο������ TableCellTextAreaRenderer �ࣩ��",
						"��Ȼ�������˵��� JTable.setDefaultRenderer() �Ǽ������ʾ����" } },
				"A B C D".split(" "));
		tbl.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());

		add(new JScrollPane(tbl));
		setSize(800, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Test12();
	}
}

class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer {
	public TableCellTextAreaRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
												   int row, int column) {
		// ���㵱���е���Ѹ߶�
		int maxPreferredHeight = 0;
		for (int i = 0; i < table.getColumnCount(); i++) {
			setText("" + table.getValueAt(row, i));
			setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
			maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height);
		}

		if (table.getRowHeight(row) != maxPreferredHeight) // ��������������Ϲæ
			table.setRowHeight(row, maxPreferredHeight);

		setText(value == null ? "" : value.toString());
		return this;
	}
}
