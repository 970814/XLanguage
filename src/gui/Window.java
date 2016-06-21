package gui;


import gui.definedidentify.DefinedIdentify;
import gui.fontdialog.FontChooser;
import gui.priority.PriorityFrame;
import moon.Moon;
import moon.compile.code.Code;
import moon.compile.function.Function;
import moon.compile.util.variable.Data;
import moon.compile.util.variable.Vars;
import moon.parse.interpreter.Interpreter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Window extends JFrame {


		static  {
//		Locale.setDefault(Locale.ENGLISH);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	{
		setTitle("Moon Ide(1.0.0.1)");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1300, 800);
		setLocationRelativeTo(null);
	}

	JTextArea console = new JTextArea(){
		{
			setFont(new Font(/*FontChooser.names[FontChooser.names.length - 11]*/"Lucida Sans Typewriter", Font.BOLD, 24));
		}
		@Override
		public void append(String str) {
			synchronized (this) {
				super.append(str);
			}
		}
	};
	Object lock = new Object();
	JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	XProject xProject = new XProject(new DefaultMutableTreeNode(new File("").getCanonicalFile(), true));
	FontChooser fontChooser = new FontChooser(this);
	JColorChooser chooser = new JColorChooser();
	DefinedIdentify definedIdentify = new DefinedIdentify();
	PriorityFrame priorityFrame = new PriorityFrame();
	JPanel fileList = new JPanel();
	JSplitPane splitPane = new JSplitPane();
	private JTextArea area;
	private Text warning = new Text("");
	public class Text extends JTextArea {
		{
			setForeground(Color.BLACK);
		}
		final int n = 110;
		private transient boolean shutdown = false;

		public boolean isShutdown() {
			return shutdown;
		}

		public void shutdown() {
			this.shutdown = true;
		}

		public Text(String s) {
			super(s);
			setFont(new Font("Dialog", Font.BOLD, 15));
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						Text.super.setText("Running");
						for (int i = 0; i < n; i++) {
							try {
								Thread.sleep(80);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							append(".");
						}
					}
				}
			}).start();
		}

		public void setText(final String t, int n) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					for (int j = 0; j < 3; j++) {
//						Text.super.setText(t);
//						for (int i = 0; i < n; i++) {
//							try {
//								Thread.sleep(500);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//							append(".");
//						}
//					}
//				}
//			}).start();
		}
	}
	Vector<File> files = new Vector<File>(){
		@Override
		public synchronized String toString() {
			StringBuilder builder = new StringBuilder(" ");
			for (File file : this) {
				builder.append(file.getName() + " ");
			}
			return builder.toString();
		}
	};
	Moon moon;

	final JScrollPane codeScrollPane;
	public Window() throws Exception {
		fileList.setBorder(new TitledBorder(BorderFactory.createLineBorder(
				Color.BLUE, 1, true), "File List", TitledBorder.LEADING,
				TitledBorder.DEFAULT_POSITION, new Font("Times Mew Roman",
				Font.BOLD, 12), Color.BLACK));


		mainSplitPane.setTopComponent(main);

		mainSplitPane.setBottomComponent(splitPane);
		splitPane.setLeftComponent(new JScrollPane(console){{
			setBorder(new TitledBorder(BorderFactory.createLineBorder(
					Color.RED, 1, true), "Console", TitledBorder.LEADING,
					TitledBorder.DEFAULT_POSITION, new Font("Times Mew Roman",
					Font.BOLD, 12), Color.BLACK));
		}});
		splitPane.setRightComponent(fileList);
		add(mainSplitPane, BorderLayout.CENTER);
		main.setRightComponent(codeScrollPane = new JScrollPane(area = xProject.getTextArea()) {
			{
				setBorder(BorderFactory.createTitledBorder(null, "Coding"));
			}
		});
		main.setLeftComponent(new JScrollPane(xProject));
		setJMenuBar(new JMenuBar() {
			{
				add(new JMenu("File") {
					{
						add(new JMenuItem("open") {
							{
								addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										JFileChooser chooser = new JFileChooser(new File(".\\"));
										int i = chooser.showOpenDialog(null);
										if (i == 0) {
											File file = chooser.getSelectedFile();
											area.setText("");
											try (Scanner scanner = new Scanner(file)) {

												do {
													area.append(scanner.nextLine() + "\n");
												} while (true);
											} catch (FileNotFoundException e1) {
//												e1.printStackTrace();
											} catch (NoSuchElementException e1) {
												//ignore.
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
										JFileChooser chooser = new JFileChooser(new File(".\\"));
										int i = chooser.showSaveDialog(null);
										if (i == 0) {
											File file = chooser.getSelectedFile();
											PrintStream out = null;
											try {
												out = new PrintStream(file);
												out.println(area.getText());
											} catch (FileNotFoundException e1) {
//												e1.printStackTrace();
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
										JFileChooser chooser = new JFileChooser(new File(".\\"));
										int i = chooser.showSaveDialog(null);
										if (i == 0) {
											File file = chooser.getSelectedFile();
											PrintStream out = null;
											try {
												out = new PrintStream(file);
												out.println(area.getText());
											} catch (FileNotFoundException e1) {
//												e1.printStackTrace();
											}
											out.close();
										}
									}
								});
								setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
							}
						});


						add(new JMenuItem("exit") {
							{
								addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										int c = JOptionPane.showConfirmDialog(Window.this, "Are you sure to quit？");
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
												Color color = JColorChooser.showDialog(Window.this, "Choose Color", area.getForeground());
												area.setForeground(color);
											}
										});
									}});
									add(new JMenuItem("Background") {{
										addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												Color color = JColorChooser.showDialog(Window.this, "Choose Color", area.getBackground());
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
												Color color = JColorChooser.showDialog(Window.this, "Choose Color", console.getForeground());
												console.setForeground(color);
											}
										});
									}});
									add(new JMenuItem("Background") {{
										addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												Color color = JColorChooser.showDialog(Window.this, "Choose Color", console.getBackground());
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
					setMnemonic('H');
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
					setMnemonic('R');
					add(new JMenuItem("Select src") {{
						addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								JFileChooser chooser = new JFileChooser(new File(".\\"));
								int i = chooser.showOpenDialog(null);
								if (i == 0) {
									File file = chooser.getSelectedFile();
									files.add(file);
									fileList.add(new JButton(file.getName()){
										JButton button = this;
										File file0 = file;
										{
											addMouseListener(new MouseAdapter() {
												@Override
												public void mouseReleased(MouseEvent e) {
													if (e.getButton() == MouseEvent.BUTTON1) {
														xProject.setNewFile(file0);
													} else if (e.getButton() == MouseEvent.BUTTON3) {
														files.remove(file0);
														fileList.remove(button);
														splitPane.setDividerLocation(0.6);
													}
												}
											});
//										addActionListener(new ActionListener() {
//											@Override
//											public void actionPerformed(ActionEvent e) {
//												files.remove(file0);
//												fileList.remove(button);
//												splitPane.setDividerLocation(0.6);
//											}
//										});
									}});
									splitPane.setDividerLocation(0.6);
								}
							}
						});
						setAccelerator(KeyStroke.getKeyStroke("ctrl shift S"));
					}});
					{
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
										try {
											moon = new Moon(files);
											warning.setText("waiting", 30);
											functionSet = moon.getFunctionSet();

											new JFrame() {
												boolean isRunning = false;
												JTextField args = new JTextField(50);
												JTextArea functionCode = new JTextArea() {{
													setFont(new Font("Times New Roman", Font.BOLD, 30));
												}};
												JScrollPane scrollPane = new JScrollPane(functionCode) {
													@Override
													public Dimension getPreferredSize() {
														return new Dimension(800, 400);
													}

													{
														setBorder(new TitledBorder("Function Code"));
													}
												};

												{
													add(new JPanel() {
														{
															add(new JLabel("run use args: "));
															add(args);
														}
													}, BorderLayout.NORTH);
													JPanel functionPanel = new JPanel(new FlowLayout());


													for (Function function : functionSet) {
														functionPanel.add(new JButton(function.toString()) {
															{
																addMouseListener(new MouseAdapter() {
																	@Override
																	public void mouseReleased(MouseEvent e) {
																		if (e.getButton() == MouseEvent.BUTTON1) {
																			StringBuilder builder = function.getSourceCode();
																			if (builder != null) {
																				functionCode.setText(builder.toString());
																			} else if (function.isNative()) {
																				functionCode.setText("Function called: \'" + function.getName() + "\' is native function.");
																			} else {
																				functionCode.setText("Function called: \'" + function.getName() + "\' has not been defined.");
																			}

																		} else if (e.getButton() == MouseEvent.BUTTON3) {
																			if (isRunning) {
																				new Thread(new Runnable() {
																					@Override
																					public void run() {
																						Toolkit.getDefaultToolkit().beep();
																						JOptionPane.showMessageDialog(null, "Cannot run more function in OneFunctionSet.");
																					}
																				}).start();
																				return;
																			}
																			isRunning = true;
																			warning.setText("Running", 30);
																			new Thread(new Runnable() {
																				Double returnValue;
																				boolean isException;
																				String error;

																				@Override
																				public void run() {
																					returnValue = null;
																					isException = false;
																					error = null;
																					new Thread(new Runnable() {
																						@Override
																						public void run() {
																							console.append("invoke: " + getActionCommand() + " {\n");
																							Queue<String> stringQueue = Function.getStringQueue();
																							while (!stringQueue.isEmpty() || (returnValue == null && !isException)) {

																								try {
																									String s = null;
																									if (stringQueue.size() > 1) {
																										s = stringQueue.remove();
																									}
																									if (s != null) {
																										console.append(s);
																									}

																								} catch (Exception e) {
																									//ignore.
																								}
																								try {
																									Thread.sleep(8);
																								} catch (InterruptedException e1) {
																									e1.printStackTrace();
																								}
																							}
																							console.append("\n");

																						}
																					}).start();
																					try {

																						String s = args.getText().trim();
																						if (s.length() == 0) {
																							returnValue = function.invoke();
																						} else {
																							String[] as = s.split(" ");
																							Data[] datas = new Data[as.length];
																							int k = 0;

																							for (int i = 0; i < as.length; i++) {
																								if (as[i].length() == 0) {
																									continue;
																								}
																								if (Character.isDigit(as[i].charAt(0)) || as[i].charAt(0) == '.') {
																									try {
																										datas[k++] = new Data(new Double(as[i]));
																									} catch (Exception e1) {
																										console.append(e1.getMessage() + ": is not a constant or valid identify.\n");
																									}
																								} else {
																									datas[k++] = new Data(as[i]);
																								}
																							}
																							datas = Arrays.copyOfRange(datas, 0, k);
																							returnValue = function.invoke(datas);
																						}
																					} catch (Exception e1) {
																						e1.printStackTrace();
																						error = e1.getMessage();
																						console.append(error);
																						isException = true;
																					} catch (StackOverflowError e2) {
																						error = "Moon.StackOverflowError";
																						console.append(error);
																						isException = true;
																					}
																					console.append("\n");
																					new Thread(new Runnable() {
																						@Override
																						public void run() {
																							flushConsole();
																							console.append("\n} return: " + returnValue);
																							Toolkit.getDefaultToolkit().beep();
																							isRunning = false;
																						}
																					}).start();
																				}
																			}).start();
																		}
																	}
																});
															}
														});

													}
													if (functionSet.size() < 1) {
														console.append("Can not find any function, in file lists: " + moon.getParserFiles());
													}
													add(functionPanel);
													add(scrollPane, BorderLayout.SOUTH);
													setTitle("Function Set");
													setDefaultCloseOperation(DISPOSE_ON_CLOSE);
													setLocationByPlatform(true);
													setAlwaysOnTop(true);
													setSize(800, 800);
												}
											}.setVisible(true);
										} catch (Exception e1) {
											console.append(e1.getMessage());
										}


									}
								});
							}
						});
						add(new JMenuItem("run this program in cmd") {{
							setAccelerator(KeyStroke.getKeyStroke("ctrl shift alt R"));
							addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									Runtime runtime = Runtime.getRuntime();
									File file0 = new File("r0.bat");
									File file = new File("r.bat");
									try {
										if (!file.exists()) {
											file.createNewFile();
										}
										PrintStream printStream = new PrintStream(file);
										printStream.println("start r0.bat");
										printStream.close();
										if (!file0.exists()) {
											file0.createNewFile();
										}
										PrintStream printStream0 = new PrintStream(file0);
										printStream0.println("@echo off\njava -jar Moon(1.0.0.1).jar " + files + "\r\npause\n");
										printStream0.close();
										runtime.exec("r.bat");
									} catch (Exception e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage());
									}
								}
							});
						}});
						add(new JMenuItem("run the latest interpreter(1.0.0.2) in cmd."){{
							setAccelerator(KeyStroke.getKeyStroke("ctrl shift alt I"));
							addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									Runtime runtime = Runtime.getRuntime();
									File file = new File("r.bat");
									try {
										if (!file.exists()) {
											file.createNewFile();
										}
										PrintStream printStream = new PrintStream(file);
										printStream.println("@echo off\nstart java -jar Inter(1.0.0.2).jar " + files);
										printStream.close();
										runtime.exec("r.bat");
									} catch (Exception e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage());
									}
								}
							});

						}});
					}
					add(new JMenuItem("flush console") {{
						setAccelerator(KeyStroke.getKeyStroke("ctrl alt F"));
						addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								Toolkit.getDefaultToolkit().beep();
								flushConsole();
							}
						});
					}});
				}});
				add(new JMenu("Clear") {{
					add(new JMenuItem("clear text") {
						{
							addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									area.setText("");
								}
							});
							setAccelerator(KeyStroke.getKeyStroke("ctrl shift T"));
						}
					});
					add(new JMenuItem("clear console") {
						{
							addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									console.setText("");
								}
							});
							setAccelerator(KeyStroke.getKeyStroke("ctrl shift C"));
						}
					});
					setMnemonic('C');
				}});
				add(new JMenu("Interpret"){{
					setMnemonic('I');
					add(new JMenuItem("open cmd"){{
						setAccelerator(KeyStroke.getKeyStroke("ctrl alt O"));
						addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								Runtime runtime = Runtime.getRuntime();
								File file = new File("r.bat");
								try {
									if (!file.exists()) {
										file.createNewFile();
									}
									PrintStream printStream = new PrintStream(file);
									printStream.println("start");
									printStream.close();
									runtime.exec("r.bat");
								} catch (Exception e1) {
									JOptionPane.showMessageDialog(null, e1.getMessage());
								}
							}
						});
					}});
					add(new JMenuItem("Open Interpret(1.0.0.1)"){{
						setAccelerator(KeyStroke.getKeyStroke("ctrl alt U"));
						addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								Runtime runtime = Runtime.getRuntime();
								try {
									runtime.exec("java -jar Inter(1.0.0.1).jar");
								} catch (IOException e1) {
									JOptionPane.showMessageDialog(null, "Cannot find jarfile.");
								}
							}
						});
					}});
					add(new JMenuItem("Open The Latest Interpret(1.0.0.2)"){
						Vars vars = new Vars();
						Interpreter interpreter = new Interpreter(vars);
						{
						setAccelerator(KeyStroke.getKeyStroke("ctrl alt L"));
						addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								new JFrame() {
									JFrame this0 = this;
									JTextArea console = new JTextArea(){{
										setFont(new Font(/*"Lucida Sans Typewriter"*/"Dialog", Font.BOLD, 16));
										setForeground(Color.WHITE);
										setBackground(Color.BLACK);
										setCaretColor(Color.WHITE);
									}};

//									JTextArea varList = new JTextArea("");
//									JPanel panel = new JPanel(){{
//										varList.setFont(new Font("Dialog", Font.ITALIC, 18));
//										add(new JScrollPane(varList));
//									}};
									JTextField field = new JTextField(){{
										setFont(new Font("Dialog", Font.BOLD, 24));
										addKeyListener(new KeyAdapter() {
											@Override
											public void keyPressed(KeyEvent e) {
												if (e.getKeyCode() == KeyEvent.VK_ENTER) {
													if (field.getText().trim().equalsIgnoreCase("exit")) {
														this0.setVisible(false);
													} else {
														run2(field, console, interpreter);
													}
												}
											}
										});
									}};
									{
										setTitle("Interpret(1.0.0.2)");
										setDefaultCloseOperation(DISPOSE_ON_CLOSE);
										setLocationByPlatform(true);
										setSize(600, 500);
										add(new JScrollPane(console) {{
											setBorder(new TitledBorder(BorderFactory.createLineBorder(
													Color.RED, 1, true), "Console", TitledBorder.LEADING,
													TitledBorder.DEFAULT_POSITION, new Font("Times Mew Roman",
													Font.BOLD, 12), Color.BLACK));
										}});
										add(new JPanel(new BorderLayout()){{
											add(new JLabel("expression: "), BorderLayout.WEST);
											add(field);
										}}, BorderLayout.SOUTH);
										setJMenuBar(new JMenuBar(){{
											add(new JMenu("Look"){{
												setMnemonic('L');
												add(new JMenuItem("var"){{
													setAccelerator(KeyStroke.getKeyStroke("ctrl alt V"));
													addActionListener(new ActionListener() {
														@Override
														public void actionPerformed(ActionEvent e) {
															new JFrame() {
																JFrame this0 = this;
																{
																	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
																	setLocationByPlatform(true);
																	setSize(400, 600);
																	JPanel panel = new JPanel();
																	add(panel);
																	for (Data var : vars) {
																		panel.add(new JButton(var.toString()) {
																			JButton button = this;
																			{
																				addMouseListener(new MouseAdapter() {
																					@Override
																					public void mouseReleased(MouseEvent e) {
																						if (e.getButton() == MouseEvent.BUTTON1) {
																							field.setText(var.getName());
																						} else if (e.getButton() == MouseEvent.BUTTON3) {
																							console.append("remove var: \'" + var.getName() + "\'");
																							panel.remove(button);
																							vars.remove(var);
																							this0.repaint();
																						}
																					}
																				});
																			}
																		});
																	}
																}
															}.setVisible(true);
														}
													});
												}});
											}});
										}});
									}
								}.setVisible(true);
							}
						});

					}});
				}});
				add(new JMenu("Look"){{
					add(new JMenuItem("history"){{
						setAccelerator(KeyStroke.getKeyStroke("ctrl shift H"));
						addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								new JFrame(){
									{
										setTitle("History");
										setDefaultCloseOperation(DISPOSE_ON_CLOSE);
										setLayout(new FlowLayout());
										setLocationByPlatform(true);
										for (Code code : history) {
											add(new JButton(code.toString()){{
												addActionListener(new ActionListener() {
													@Override
													public void actionPerformed(ActionEvent e) {
														try {
															console.append(getActionCommand() + " -> " + interpreter.interpret(code));
														} catch (Exception e1) {
															console.append(e1.getMessage());
														}
														console.append("\n");
													}
												});
											}});
										}
										if (history.size() < 1) {
											add(new JLabel("Has no history") {{
												setFont(new Font("Dialog", Font.BOLD | Font.PLAIN, 45));
											}});
										}
										setSize(500, 500);
									}}.setVisible(true);
							}
						});
					}});
					add(new JMenuItem("contact us"){{
						addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								new JFrame(){{
									setTitle("About Us");
									setLocationRelativeTo(Window.this);
									setDefaultCloseOperation(DISPOSE_ON_CLOSE);
									setSize(850, 500);
									add(new JLabel(){
										@Override
										protected void paintComponent(Graphics g) {
											Graphics2D d = (Graphics2D) g;
											d.setFont(new Font("Dialog", Font.BOLD, 24));
											d.drawString("欢迎使用Moon(1.0.0.1)!", 100, 50);
											d.drawString("感谢您的支持!", 100, 80);
											d.drawString("虽然我们刚刚起步,", 100, 110);
											d.drawString("但我们是一个庞大的团队!", 100, 140);
											d.drawString("请联系我们的CEO: 刘先生; 邮箱: 1421053434@qq.com.", 100, 170);

											d.drawString("-------------------------------------------------------------------------------", 100, 210);
											d.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 22));
											d.drawString("Welcome to use Moon(1.0.0.1)!", 100, 265);
											d.drawString("Thanks for your support!", 100, 290);
											d.drawString("Although we have just started,", 100, 315);
											d.drawString("But we are a large team!", 100, 340);
											d.drawString("Please contact our CEO: Mr. L; mail: 1421053434@qq.com.", 100, 375);
										}
									});
								}
								}.setVisible(true);
							}
						});
					}});
					add(new JMenuItem("more infos"){{
						addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								new JFrame() {
									JTextArea area = new JTextArea() {{
//										setEditable(false);
										setFont(new Font("Dialog", Font.BOLD | Font.PLAIN, 22));
									}};
									{
										setTitle("About Us");
										setLocationRelativeTo(Window.this);
										setDefaultCloseOperation(DISPOSE_ON_CLOSE);
										setSize(850, 500);
										add(new JScrollPane(area));
										setJMenuBar(new JMenuBar(){{

											add(new JMenu("Look"){{
												setMnemonic('L');
												add(new JMenuItem("readme"){{
													setAccelerator(KeyStroke.getKeyStroke("ctrl M"));

													addActionListener(new ActionListener() {
														@Override
														public void actionPerformed(ActionEvent e) {
															try (Scanner scanner = new Scanner(new File("readme.txt"))) {
																try {
																	while (true) {
																		area.append(scanner.nextLine() + "\n");
																	}
																} catch (NoSuchElementException e1) {
//																	e1.printStackTrace();
																	//ignore it.
																}
															} catch (FileNotFoundException e1) {
																JOptionPane.showMessageDialog(null, e1.getMessage());
															}
														}
													});
												}});

											}});
										}});
									}
								}.setVisible(true);
							}
						});
					}});
					setMnemonic('L');
				}});
			}
		});

		add(new JPanel(new BorderLayout()) {
			{
				add(new JPanel(new BorderLayout()){
					{
						add(new JPanel(new BorderLayout()) {
							{
								add(new JLabel("exp: "), BorderLayout.WEST);
								add(expression);
							}
						}, BorderLayout.WEST);
						add(new JPanel(new BorderLayout()) {
							{
								add(new JLabel("input: "), BorderLayout.WEST);
								add(input);
							}
						}, BorderLayout.EAST);
					}
				}, BorderLayout.NORTH);
				add(new JLabel("warning: "), BorderLayout.WEST);
				add(warning);
			}
		}, BorderLayout.SOUTH);
	}

	private void flushConsole() {
		Queue<String> strings = Function.getStringQueue();
		try {
			while (!strings.isEmpty()) {
				console.append(strings.remove());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			//ignore this expection
//			flushConsole();

		}
	}


	private final JTextField input = new JTextField(30){{
		setFont(new Font("Dialog", Font.BOLD, 18));
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String in = input.getText().trim();
					input.setText("");
					if (in.length() > 0) {
						Function.getInputStringBuilder().add(in);
						console.append("input -> \'" + in + "\'\n");
					}
				}
			}
		});
	}};
	JTextField expression = new JTextField(30){{
		setFont(new Font("Dialog", Font.BOLD, 24));
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					run2(expression, console, interpreter);
				}
			}
		});
	}};

	private void run2(JTextField expression, JTextArea console,Interpreter interpreter) {
		String exp = expression.getText().trim();
		if (exp.length() > 0) {
			try {
				console.append(exp + " -> " + calValue(interpreter,exp));
			} catch (Exception e1) {
				console.append("For \'" + exp + "\' :" + e1.getMessage());
			}
		} else {
			console.append("Empty expression!");
		}
		console.append("\n");
		expression.setText("");
		Toolkit.getDefaultToolkit().beep();
	}
	Vars vars = new Vars();
	Interpreter interpreter = new Interpreter(vars);
	LinkedList<Code> history = new LinkedList<>();

	private double calValue(Interpreter interpreter, String exp) throws Exception {
		if (exp.length() > 0) {
			Code code = interpreter.compile0(exp);
			double returnValue = interpreter.interpret(code);
			history.add(code);
			return returnValue;
		}
		return -1.0;
	}

	Vector<Function> functionSet = null;
	public static void main(String[] args) throws Exception {
		Window window = new Window();
		window.setVisible(true);
		window.setLayout();
	}

	private void setLayout() {
		main.setDividerLocation(0.3);
		mainSplitPane.setDividerLocation(0.8);
		splitPane.setDividerLocation(0.6);
	}
}
