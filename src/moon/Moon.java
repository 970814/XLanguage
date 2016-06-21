package moon;

import moon.compile.function.Function;
import moon.parse.Parser;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Administrator on 2016/6/3.
 */
public class Moon {
	final Parser parser;
	private static String out = null;
//	public static final Queue<String> stringQueue = new LinkedList<>();
	public Moon(Vector<File> files) throws Exception {
		File[] files0 = new File[files.size()];
		for (int i = 0; i < files0.length; i++) {
			files0[i] = files.get(i);
		}
		this.parser = new Parser(files0);
	}

	public static void setOut(String out) {
		Moon.out = out;
	}

	public Moon(File... files) throws Exception {
		this.parser = new Parser(files);
	}

	public Moon(String... files) throws Exception {
		this.parser = new Parser(files);
	}

	public static String getOut() {
		return out;
	}

	public static File getOutFile() {
		return new File(".\\" + Moon.getOut());
	}

	public Vector<Function> getFunctionSet() throws Exception {
		return parser.compileAll();
	}

	public Function getMainFunction(Vector<Function> functionSet)
			throws Exception {
		for (Function function : functionSet) {
			if (function.getName().equals("main")) {
				return function;
			}
		}
		throw new Exception("在文件列表: \'" + parser.allFiles + "\' 找不到main函数原型.");
	}

	public LinkedList<File> getParserFiles() {
		return parser.allFiles;
	}

	private static File parent = new File("workspace");

	public static File getParent() {
		return parent;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			Moon moon = new Moon(args);
			Function.setStringQueue(null);
			Function.setInputStringBuilder(null);
			Vector<Function> functionSet = moon.getFunctionSet();
			Function mainFunction = moon.getMainFunction(functionSet);
			double r = mainFunction.invoke();
			System.out.println("return -> " + r);
			return;
		} else {
			JOptionPane.showMessageDialog(null, "There is no input file!");
		}

	}

	public static void clearFile() throws FileNotFoundException {
		File file = getOutFile();
		new PrintStream(file).print("");
	}
}
