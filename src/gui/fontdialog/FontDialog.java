package gui.fontdialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/8.
 */
public class FontDialog extends JFrame {
    Font font;
    Font font0;
    final JTextArea textArea;

    static String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    static String[] styles = new String[]{"PLAIN", "BOLD", "ITALIC", "BOLD & ITALIC"};
    static Integer[] integers = new Integer[]{4, 6, 8, 10, 12, 16, 18, 24, 28, 32, 36, 40, 46, 52, 64, 76, 108};

    static JList<String> fontFamily = new JList<>(names);
    static JList<String> fontStyle = new JList<>(styles);
    static JList<Integer> fontSize = new JList<>(integers);
    final JTextField name = new JTextField();
    final JTextField sty = new JTextField();
    final JTextField siz = new JTextField();
    final JTextArea example = new JTextArea("Hello, world!");
    JButton apply = new JButton("Apply");
    JButton reset = new JButton("Reset");

    public FontDialog(JTextArea text) {
        font = getFont();
        textArea = text;
        example.setLineWrap(true);
        siz.setEditable(false);
        sty.setEditable(false);
        name.setEditable(false);
        JPanel family = new JPanel(new BorderLayout());
        JPanel style = new JPanel(new BorderLayout());
        JPanel size = new JPanel(new BorderLayout());
        family.setBorder(new TitledBorder("Font - Family"));
        family.add(name, BorderLayout.NORTH);
        family.add(new JScrollPane(fontFamily));
        style.setBorder(new TitledBorder("Font - Style"));
        style.add(sty, BorderLayout.NORTH);
        style.add(new JScrollPane(fontStyle));
        size.setBorder(new TitledBorder("Font - Size"));
        size.add(siz, BorderLayout.NORTH);
        size.add(new JScrollPane(fontSize));


        JPanel mainPanel = new JPanel(new GridLayout(1, 3));
        mainPanel.add(family);
        mainPanel.add(style);
        mainPanel.add(size);
        add(mainPanel, BorderLayout.NORTH);
        add(new JScrollPane(example) {{
            setBorder(new TitledBorder(BorderFactory.createLineBorder(
                    Color.BLUE, 2, true), "Example", TitledBorder.LEADING,
                    TitledBorder.DEFAULT_POSITION, new Font("Times Mew Roman",
                    Font.BOLD | Font.ITALIC, 20), Color.RED));
        }}, BorderLayout.CENTER);
        add(new JScrollPane(new JPanel() {
            {
                add(apply);
                add(reset);
            }
        }), BorderLayout.SOUTH);
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(getFont());
                font = getFont();
            }
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFont(font);
            }
        });

    }

    private Object setSelectionValue(JTextField field, String[] names, String text) {
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(text)) {
                field.setText(names[i]);
                return names[i];
            }
        }
        return null;
    }

    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setSize(500, 500);
    }

    public void setFont(Font f) {
        super.setFont(f);
        font = getFont();
        fontFamily.setSelectedValue(setSelectionValue(name, names, font.getName()), true);
        fontStyle.setSelectedValue(setSelectionValue(sty, styles, styles[font.getStyle()]), true);
        final int size = font.getSize();
        siz.setText(String.valueOf(size));
        boolean flag = false;
        for (int i = 0; i < integers.length; i++) {
            if (integers[i] == size) {
                fontSize.setSelectedValue(integers[i], true);
                flag = true;
                break;
            }
        }
        fontSize.setSelectedValue(null, true);
        example.setFont(f);
        textArea.setFont(f);
    }

    ListSelectionListener listSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            String n = fontFamily.getSelectedValue();
            String s = fontStyle.getSelectedValue();
            int i = fontSize.getSelectedValue();
            name.setText(n);
            sty.setText(s);
            siz.setText("" + i);
            FontDialog.super.setFont(new Font(n, fontStyle.getSelectedIndex(), i));
            font0 = getFont();
            example.setFont(getFont());
        }
    };
    private void addListener() {
        fontSize.addListSelectionListener(listSelectionListener);
        fontFamily.addListSelectionListener(listSelectionListener);
        fontStyle.addListSelectionListener(listSelectionListener);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception evt) {
        }
        FontDialog fontDialog = new FontDialog(new JTextArea());
        fontDialog.setVisible(true);
        fontDialog.addListener();
    }
}
