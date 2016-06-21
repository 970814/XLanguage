package gui;


import gui.definedidentify.DefinedIdentify;
import gui.fontdialog.FontChooser;
import gui.priority.PriorityFrame;
import moon.Moon;
import moon.compile.function.Function;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class Window0 extends JFrame {
    //	static  {
////		Locale.setDefault(Locale.ENGLISH);
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
//	}
    {
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 900);
        setState(MAXIMIZED_BOTH);
    }

    JTextArea console = new JTextArea();
    JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    XProject xProject = new XProject(new DefaultMutableTreeNode(new File("").getCanonicalFile(), true));
    FontChooser fontChooser = new FontChooser(this);
    DefinedIdentify definedIdentify = new DefinedIdentify();
    PriorityFrame priorityFrame = new PriorityFrame();
    JPanel fileList = new JPanel();
    JSplitPane splitPane = new JSplitPane();
    private JTextArea area;
    private JTextField warning = new JTextField(":");
    Vector<File> files = new Vector<>();
    Moon moon;

    public Window0() throws Exception {
        fileList.setBorder(new TitledBorder(BorderFactory.createLineBorder(
                Color.BLUE, 1, true), "File List", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, new Font("Times Mew Roman",
                Font.BOLD, 12), Color.BLACK));
        console.setBorder(new TitledBorder(BorderFactory.createLineBorder(
                Color.RED, 1, true), "Console", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, new Font("Times Mew Roman",
                Font.BOLD, 12), Color.BLACK));
//        splitPane.setLeftComponent(new JScrollPane(console));
        splitPane.setRightComponent(fileList);
        add(main, BorderLayout.CENTER);
        main.setRightComponent(new JScrollPane(area = xProject.getTextArea()));
        area.setBorder(BorderFactory.createTitledBorder(null, "Coding"));
        main.setLeftComponent(new JScrollPane(xProject));
        new JFrame("Console"){{
            add(new JScrollPane(console));
            pack();
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationByPlatform(true);
        }}.setVisible(true);
        new JFrame("File List") {{
            add(splitPane);
            pack();
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationByPlatform(true);
        }}.setVisible(true);
        setJMenuBar(new JMenuBar() {
            {
                add(new JMenu("File") {
                    {
                        add(new JMenuItem("open") {
                            {
                                addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        JFileChooser chooser = new JFileChooser();
                                        int i = chooser.showOpenDialog(null);
                                        if (i == 0) {
                                            File file = chooser.getSelectedFile();
                                            area.setText("");
                                            try (Scanner scanner = new Scanner(file)) {

                                                do {
                                                    area.append(scanner.nextLine());
                                                } while (true);
                                            } catch (FileNotFoundException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    }
                                });
                                setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
                            }
                        });

                        add(new JMenuItem("new") {
                            {
                                addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        JFileChooser chooser = new JFileChooser();
                                        int i = chooser.showSaveDialog(null);
                                        if (i == 0) {
                                            File file = chooser.getSelectedFile();
                                            PrintStream out = null;
                                            try {
                                                out = new PrintStream(file);
                                                out.println(area.getText());
                                            } catch (FileNotFoundException e1) {
                                                e1.printStackTrace();
                                            }
                                            out.close();
                                            area.setText("");
                                        }
                                    }
                                });
                                setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
                            }
                        });
                        add(new JMenuItem("save") {
                            {
                                addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        JFileChooser chooser = new JFileChooser();
                                        int i = chooser.showSaveDialog(null);
                                        if (i == 0) {
                                            File file = chooser.getSelectedFile();
                                            PrintStream out = null;
                                            try {
                                                out = new PrintStream(file);
                                                out.println(area.getText());
                                            } catch (FileNotFoundException e1) {
                                                e1.printStackTrace();
                                            }
                                            out.close();
                                        }
                                    }
                                });
                                setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
                            }
                        });
                        add(new JMenuItem("clear") {
                            {
                                addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        area.setText("");
                                    }
                                });
                                setAccelerator(KeyStroke.getKeyStroke("ctrl shift C"));
                            }
                        });

                        add(new JMenuItem("exit") {
                            {
                                addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        int c = JOptionPane.showConfirmDialog(Window0.this, "Are you sure to quit？");
                                        if (c == 0) {
                                            System.exit(0);
                                        }
                                    }
                                });
                                setAccelerator(KeyStroke.getKeyStroke("ctrl alt shift E"));
                            }
                        });
                        setMnemonic('F');
                    }
                });
                add(new JMenu("Settings"){
                    {
                        add(new JMenu("text"){
                            {
                                add(new JMenuItem("Font") {
                                    {
                                        addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                fontChooser.show(area.getFont(), area);
                                            }
                                        });
                                    }
                                });
                                add(new JMenu("Color") {{
                                    add(new JMenuItem("Foreground") {{
                                        addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                Color color = JColorChooser.showDialog(Window0.this, "Choose Color", area.getForeground());
                                                area.setForeground(color);
                                            }
                                        });
                                    }});
                                    add(new JMenuItem("Background") {{
                                        addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                Color color = JColorChooser.showDialog(Window0.this, "Choose Color", area.getBackground());
                                                area.setBackground(color);
                                            }
                                        });
                                    }});
                                }});
                            }
                        });

                        add(new JMenu("console"){
                            {
                                add(new JMenuItem("Font") {
                                    {
                                        addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                fontChooser.show(console.getFont(), console);
                                            }
                                        });
                                    }
                                });
                                add(new JMenu("Color") {{
                                    add(new JMenuItem("Foreground") {{
                                        addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                Color color = JColorChooser.showDialog(Window0.this, "Choose Color", console.getForeground());
                                                console.setForeground(color);
                                            }
                                        });
                                    }});
                                    add(new JMenuItem("Background") {{
                                        addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                Color color = JColorChooser.showDialog(Window0.this, "Choose Color", console.getBackground());
                                                console.setBackground(color);
                                            }
                                        });
                                    }});
                                }});
                            }
                        });
                        setMnemonic('S');
                    }
                });
                add(new JMenu("Help"){{
                    add(new JMenuItem("view DID"){{
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                definedIdentify.setVisible(true);
                            }
                        });
                    }});
                    add(new JMenuItem("view PRIORITY") {{
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                priorityFrame.setVisible(true);
                            }
                        });
                    }});
                }});
                add(new JMenu("Run"){{
                    add(new JMenuItem("Select src") {{
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFileChooser chooser = new JFileChooser(new File(".\\"));
                                int i = chooser.showOpenDialog(null);
                                if (i == 0) {
                                    File file = chooser.getSelectedFile();
                                    files.add(file);
                                    fileList.add(new JButton(file.getName()));
                                    splitPane.setDividerLocation(0.6);
                                }
                            }
                        });
                        setAccelerator(KeyStroke.getKeyStroke("ctrl shift S"));
                    }});
                    add(new JMenuItem("run") {

                        {
                            setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
                            addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (files.size() <= 0) {
                                        JOptionPane.showMessageDialog(null, "you have selected no file.");
                                        return;
                                    }
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                moon = new Moon(files);
                                                warning.setText("waiting...");
                                                functionSet = moon.getFunctionSet();

                                                new JFrame() {
                                                    boolean isRunning = false;
                                                    {

                                                        setTitle("Function Set");
                                                        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                                        setLocationByPlatform(true);
                                                        setAlwaysOnTop(true);
//													setBorder(new TitledBorder("Function Set"));
                                                        setLayout(new FlowLayout());
                                                        for (Function function : functionSet) {
                                                            add(new JButton(function.toString()) {
                                                                {
                                                                    addActionListener(new ActionListener() {
                                                                        @Override
                                                                        public void actionPerformed(ActionEvent e) {
																			if (isRunning) {
                                                                                JOptionPane.showMessageDialog(null, "Function has already been running.");
                                                                                return;
                                                                            }
                                                                            isRunning = true;
                                                                            new Thread(new Runnable() {
                                                                                @Override
                                                                                public void run() {

                                                                                    double returnValue;
                                                                                    try {
                                                                                        returnValue = function.invoke();
                                                                                        console.append("invoke: " + getActionCommand() + " {\n");
                                                                                        while (!Function.getStringQueue().isEmpty()) {
                                                                                            System.out.print(Function.getStringQueue().remove());
//																							Function.getStringQueue().remove()
																							console.append("f");
                                                                                        }
                                                                                        console.append("} return: " + returnValue);
                                                                                        System.out.print("} return: " + returnValue);
                                                                                    } catch (Exception e1) {
                                                                                        e1.printStackTrace();
                                                                                        console.append(e1.getMessage());
                                                                                    }
//                                                                                    console.append("\n");
                                                                                    System.out.println("end");
																					isRunning = false;
                                                                                }
                                                                            }).start();
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                        }
                                                        pack();
                                                    }
                                                }.setVisible(true);
                                            } catch (Exception e1) {
                                                console.append(e1.getMessage());
                                            }
                                        }
                                    }).start();

                                }
                            });
                        }
                    });
                }});
            }
        });
        add(warning, BorderLayout.SOUTH);
    }

    Vector<Function> functionSet = null;
    public static void main(String[] args) throws Exception {
        Window0 window = new Window0();
        window.setVisible(true);
        window.setLayout();
    }

    private void setLayout() {
        main.setDividerLocation(0.3);
        splitPane.setDividerLocation(0.6);

    }
}
