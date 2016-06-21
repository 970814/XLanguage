package gui.definedidentify;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class DefinedIdentify extends JFrame {

	{
		setTitle("All key word");
		JScrollPane pane = new JScrollPane(new KeyLabel());
		pane.setBorder(new TitledBorder(BorderFactory.createLineBorder(
				Color.BLUE, 2, true), "Key Word", TitledBorder.LEADING,
				TitledBorder.DEFAULT_POSITION, new Font("Times Mew Roman",
						Font.BOLD | Font.ITALIC, 20), Color.RED));
		add(pane);
		setLocationByPlatform(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 400);
	}
	Vector<String> priority = new Vector<String>();

	public static void main(String[] args) {
		new DefinedIdentify().setVisible(true);
	}
}
