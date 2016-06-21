package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import tctar.LabelPanel;
import tctar.Priority;

public class PriorityTest implements Serializable{
	public static void main(String[] args) {

		new JFrame() {
			{
				JScrollPane pane = new JScrollPane(new LabelPanel());
				pane.setBorder(
						new TitledBorder(null, "Operator priority", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
								new Font("Times Mew Roman", Font.BOLD | Font.ITALIC, 20), Color.BLUE));
				add(pane);
				setSize(500, 500);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLocationByPlatform(true);
			}
		}.setVisible(true);

	}
}
