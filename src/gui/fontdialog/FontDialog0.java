package gui.fontdialog;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/8.
 */
public class FontDialog0 extends JFrame {
    Font font = null;

    JList<String> fontFamily = new JList<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    JList<String> fontStyle = new JList<>(new String[]{"PLAIN", "BOLD", "ITALIC", "BOLD & ITALIC"});
    JList<Integer> fontSize = new JList<>(new Integer[]{2, 4, 6, 8, 10, 12, 16, 18, 32, 64});
    final JTextArea textArea = new JTextArea();
    public FontDialog0() {
        JPanel family = new JPanel(new BorderLayout());
        JPanel style = new JPanel(new BorderLayout());
        JPanel size = new JPanel(new BorderLayout());
        family.add(new JLabel("Font - Family"), BorderLayout.NORTH);
        family.add(fontFamily);
        style.add(new JLabel("Font - Style"), BorderLayout.NORTH);
        style.add(fontStyle);
        size.add(new JLabel("Font - Size"), BorderLayout.NORTH);
        size.add(fontSize);


        JPanel mainPanel = new JPanel();
        mainPanel.add(family);
        mainPanel.add(style);
        mainPanel.add(size);
        add(mainPanel, BorderLayout.NORTH);
    }

    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setSize(500, 500);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception evt) {
        }
//        new FontDialog().setVisible(true);
        System.out.println(Arrays.toString(UIManager.getInstalledLookAndFeels()));
    }
}
