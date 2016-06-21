package test;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class Test11 {
	public static void main(String[] args) {
		Object[][] cellData = { { "row1-col1", "row1-col2" },
				{ "row2-col1", "row2-col2" } };
		String[] columnNames = { "col1", "col2" };

		JTable table = new JTable(cellData, columnNames);
		final JScrollPane pane = new JScrollPane(table);
		JFrame jf = new JFrame() {
			{
				setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				setLocationByPlatform(true);
				add(pane);
				pack();
			}
		};
		jf.setVisible(true);
	}
}
