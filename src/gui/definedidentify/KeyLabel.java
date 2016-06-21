package gui.definedidentify;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import moon.compile.util.analyst.constant.KeyWord;
import moon.compile.util.analyst.constant.ReservedWords;

public class KeyLabel extends JPanel {
	public KeyLabel() {
		Vector<String[]> priority = KeyWord.STRINGS;
		setLayout(new GridLayout(0, 3));
		for (int i = 1; i < priority.size(); i++) {
			for (int j = 0; j < priority.get(i).length; j++) {
				add(new JLabel(priority.get(i)[j]) {
					{
						setFont(new Font("Times Mew Roman", Font.BOLD, 17));
					}
				});
			}
		}
		Vector<String> ss = ReservedWords.getReservedWords();
		for (String s : ss) {
			add(new JLabel(s) {
				{
					setFont(new Font("Times Mew Roman", Font.BOLD, 17));
				}
			});
		}
	}
}
