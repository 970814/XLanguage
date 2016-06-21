package moon.compile;

import moon.compile.util.analyst.priority.Operator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/5/21.
 */
public class StringAnalyst extends Operator {
	CompileStack compileStack = new CompileStack();
	Queue<StringBuilder> codes = new LinkedList<>();
	private boolean isDefining = false;

	public void defining(boolean defining) {
		isDefining = defining;
	}

	public void analysis(String s) throws Exception {
		int i = -1;
		compileStack.clear();
		codes.clear();

		int size = s.length();
		char[] chs = new char[size];
		toCharArray(s, chs);
		StringBuilder code = new StringBuilder();
		boolean isLast = false;
		int lines = 0;
		try {
			while (++i < size) {
				char ch = chs[i];
				if (!isLast && (ch == ' ' || ch == '\t')) {
					continue;
				}
				if (ch == '\n' || ch == '\r') {
					lines++;
					compileStack.add(ch);

					if (isLast) {
						code.append(' ');
					}
					continue;
				}
				if (ch == ';' || ch == '{' || ch == '}') {

					isLast = false;
					if (code.length() > 0) {
						if (code.toString().startsWith("def") && (code.length() > 3 ? !SourceCodeAnalyst.isVaildCharacter(code
								.charAt(3)) : true)) {
							if (isDefining) {
								codes.add(code);
								compileStack.add(CODE, false);
							}
						} else if (!isDefining) {
							codes.add(code);
							compileStack.add(CODE, false);
						}
					}

					if (ch != ';') {
						compileStack.add(ch);
					}
					code = new StringBuilder();
					continue;
				}
				code.append(ch);
				isLast = true;
			}
			compileStack.check();
			compileStack.toExp();
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("源代码结构错误:解析到第" + (++lines) + "行, 第" + (i + 1)
					+ "个字符: \'" + String.format("%#x", (int) chs[i - 1]) + "\': " + e.getMessage());
		}
//		System.out.println(compileStack);
//		JOptionPane.showMessageDialog(null, "end:"+compileStack);
//		System.exit(1);
	}

	public static void main(String[] args) {
		System.out.println();
		StringAnalyst analyst = new StringAnalyst();
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				try {
					analyst.analysis(scanner.nextLine());
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(analyst.compileStack);
				System.out.println(analyst.codes);
			} while (true);
		}
	}
}
