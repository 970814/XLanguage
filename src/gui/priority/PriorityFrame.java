package gui.priority;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import tctar.LabelPanel;

public class PriorityFrame extends JFrame {
	public PriorityFrame() {
		setTitle("Each priority between operator and operator");
		JScrollPane pane = new JScrollPane(new LabelPanel());
		pane.setBorder(new TitledBorder(BorderFactory.createLineBorder(
				Color.BLUE, 2, true), "Operator priority",
				TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font(
						"Times Mew Roman", Font.BOLD | Font.ITALIC, 20),
				Color.BLUE));
		// pane.setBorder();
		add(pane);
		setSize(700, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
	}

	public static void main(String[] args) {
		new PriorityFrame().setVisible(true);
	}
}
