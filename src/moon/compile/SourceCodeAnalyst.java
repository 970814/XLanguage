package moon.compile;

import moon.compile.sourcecode.*;
import moon.compile.util.analyst.constant.BaseDataConstant;
import moon.compile.util.variable.Vars;
import moon.compile.function.Function;

import java.util.*;

/**
 * Created by Administrator on 2016/5/21.
 */
public class SourceCodeAnalyst implements BaseDataConstant {

	Vars vars = null;

	public void defineFunction(Vars vars) {
		this.vars = vars;
	}

	public void nonDefineFunction() {
		vars = null;
	}

	CodeSet head = new CodeSet(null);

	public CodeSet compile(CompileStack compileStack, Queue<StringBuilder> codes)
			throws Exception {

		head = new CodeSet(null);
		CodeSet pointer = head;
		Stack<Character> stack = new Stack<Character>() {
			{
				add('{');
			}
		};
		int which = 0;
//		JOptionPane.showMessageDialog(null, compileStack);
		compileStack.remove(0);
		int lines = 0;

		try {
			while (!stack.empty()) {
				if (pointer.size() > 0) {
					pointer.getLast().setLine(lines);
				}
				char ch = compileStack.remove(0);
				if (ch == '\n' || ch == '\r') {
					lines++;
					continue;
				}
				if (ch == CODE) {
					which++;
					checkIfElseWhile(pointer);
					StringBuilder code = codes.remove();
					if (code.toString().startsWith("return")
							&& (code.length() > 6 ? !isVaildCharacter(code
							.charAt(6)) : true)) {
						code.delete(0, 6);
						if (code.toString().trim().length() == 0) {
							throw new Exception(
									"behind \'return\' expect expression.");
						}
						ReturnSourceCode src = new ReturnSourceCode(code.toString());
						pointer.add(src);
					} else if (code.toString().startsWith("if")
							&& (code.length() > 2 ? !isVaildCharacter(code
							.charAt(2)) : true)) {
						code.delete(0, 2);
						if (code.toString().trim().length() == 0) {
							throw new Exception(
									"behind \'if\' expect conditional expression.");
						}
						IfSourceCode src = new IfSourceCode(code.toString());
						pointer.add(src);
//						JOptionPane.showMessageDialog(null, compileStack);
						if (compileStack.get(0) != '{') {
							throw new Exception(
									"behind the expression of \'if\', expect: \'{value_if_true}\'");
						}
					} else if (code.toString().startsWith("else")
							&& (code.length() > 4 ? !isVaildCharacter(code
							.charAt(4)) : true)) {
						code.delete(0, 4);
						if (code.toString().trim().length() != 0) {
							throw new Exception(
									"behind the expression of \'else\', expect: \'{code-statement}\'");
						}
						SourceCode last = pointer.get(pointer.size() - 1);
						if (pointer.size() == 0
								|| !(last instanceof IfSourceCode)
								|| (last instanceof IfSourceCode)
								&& ((IfSourceCode) last).getValue_if_false() != null) {
							throw new Exception("\'else\' without if");
						}
						((IfSourceCode) last).hasElse();
//						JOptionPane.showMessageDialog(null, compileStack);
						if (compileStack.get(0) != '{') {
							throw new Exception(
									"behind the expression of \'if-else\', expect: \'{value_if_false}\'");
						}
					} else if (code.toString().startsWith("while")
							&& (code.length() > 5 ? !isVaildCharacter(code
							.charAt(5)) : true)) {
						code.delete(0, 5);
						if (code.toString().trim().length() == 0) {
							throw new Exception(
									"behind \'while\' expect conditional expression.");
						}
						WhileSourceCode src = new WhileSourceCode(
								code.toString());
						pointer.add(src);
						if (compileStack.get(0) != '{') {
							throw new Exception(
									"behind the expression of \'while\', expect: \'{loop-statement}\'");
						}
					} else if (vars != null && code.toString().startsWith("def") && (code.length() > 3 ? !isVaildCharacter(code
							.charAt(3)) : true)) {
						code.delete(0, 3);
//						System.out.println(Arrays.toString(code.toString().toCharArray()));
						vars.newVar(Function.defineFunction(new StringBuilder(code.toString().trim())));
					} else {
						pointer.add(new SourceCode(code.toString()));
					}


				} else if (ch == '{') {
					stack.add(ch);
					CodeSet codeBlock = new CodeSet(pointer);
					if (pointer.size() > 0) {
						SourceCode last = pointer.get(pointer.size() - 1);
						if (last instanceof IfSourceCode) {
							IfSourceCode ifCode = (IfSourceCode) last;
							if (ifCode.getValue_if_true() == null) {
								ifCode.setValue_if_true(codeBlock);
								pointer = codeBlock;
								continue;
							}
							if (ifCode.isHasElse()
									&& ifCode.getValue_if_false() == null) {
								ifCode.setValue_if_false(codeBlock);
								pointer = codeBlock;
								continue;
							}
						}
						if (last instanceof WhileSourceCode) {
							WhileSourceCode whileCode = (WhileSourceCode) last;
							if (whileCode.getLoop() == null) {
								whileCode.setLoop(codeBlock);
								pointer = codeBlock;
								continue;
							}
						}
					}
					pointer.add(new SourceCode(codeBlock));
					pointer = pointer.getLast().getSourceCodes();
				} else if (ch == '}') {
					checkIfElseWhile(pointer);
					stack.pop();
					pointer = pointer.getPrevious();
				} else {
					System.err.println("exception: -2");
					System.exit(-2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("源代码语法分析阶段异常: 解析到第" + (++lines) + "行(逻辑行); 第" + (which + 1) + "条代码: "
					+ e.getMessage());
		}
		return head;
	}

	private void checkIfElseWhile(CodeSet pointer) throws Exception {
		if (pointer.size() > 0) {
			SourceCode sourceCode = pointer.get(pointer.size() - 1);
			if (sourceCode instanceof IfSourceCode) {
				IfSourceCode ifCode = (IfSourceCode) sourceCode;
				if (ifCode.getValue_if_true() == null) {
					ifCode.setValue_if_true(new CodeSet(pointer));
//					throw new Exception(
//							"behind the expression of \'if\', expect: \'{value_if_true}\'");
				}
				if (ifCode.isHasElse() && ifCode.getValue_if_false() == null) {
					ifCode.setValue_if_false(new CodeSet(pointer));
//					throw new Exception(
//							"behind the expression of \'if-else\', expect: \'{value_if_false}\'");
				}
			}
			if (sourceCode instanceof WhileSourceCode) {
				WhileSourceCode whileCode = (WhileSourceCode) sourceCode;
				if (whileCode.getLoop() == null) {
					whileCode.setLoop(new CodeSet(pointer));
//					throw new Exception(
//							"behind the expression of \'while\', expect: \'{loop-statement}\'");
				}
			}
		}
	}

	public static boolean isVaildCharacter(char ch) {
		return Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '_';
	}

	public static void main(String[] args) {
		SourceCodeAnalyst sourceCodeAnalyst = new SourceCodeAnalyst();
		System.out.println(sourceCodeAnalyst.isVaildCharacter(' '));

		StringAnalyst analyst = new StringAnalyst();
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				try {
					analyst.analysis(scanner.nextLine());
					System.out.println("compileStack: " + analyst.compileStack);
					System.out.println("codes: " + analyst.codes);
					sourceCodeAnalyst.compile(analyst.compileStack,
							analyst.codes);
					System.out.println(sourceCodeAnalyst.head);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} while (true);
		}
	}
}
