package tctar;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import priorityArray.PriorityArray;

public class Priority extends JPanel {
	private Image img = null;
	private Graphics gp = null;

	public Image getImg() {
		return img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D d = (Graphics2D) g;
		if (img == null) {
			img = createImage(2000, 2000);
			gp = img.getGraphics();
//			gp.setFont(getFont().deriveFont(10.0f));
			p(gp);
		} else {

			d.drawImage(img, 0, 0, null);
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(2000, 2000);
	}

	private static void p(Graphics d) {
		String[][] priority = PriorityArray.operatorPriority();
		final int width = 2000;
		final int height = 2000;
		final int per_w = width / priority.length + 1;
		final int per_h = height / priority.length + 1;

		for (int i = 0, y = 10; i < priority.length; i++, y += per_h) {
			for (int j = 0, x = 0; j < priority.length; j++, x += per_w) {
				d.drawString(priority[i][j], x, y);
			}
		}
	}
}
