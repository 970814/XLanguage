package tctar;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import priorityArray.PriorityArray;

public class LabelPanel extends JPanel {
	public LabelPanel() {
		final String[][] priority = PriorityArray.operatorPriority();
		setLayout(new GridLayout(priority.length, priority.length));
		for (final int[] i = new int[] { 0 }; i[0] < priority.length; i[0]++) {
			for (final int[] j = new int[] { 0 }; j[0] < priority.length; j[0]++) {
				add(new JLabel(priority[i[0]][j[0]]) {
					{
						setToolTipText(priority[i[0]][0] + ", "
								+ priority[j[0]][0]);
						//setBorder(BorderFactory.createTitledBorder(""));
						if (i[0] == 0 || j[0] == 0) {
							setForeground(Color.RED);
						}
					}
				});
			}
		}
	}

}
