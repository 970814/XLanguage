package moon.compile.util.analyst;

import java.util.Stack;

import moon.compile.util.analyst.priority.Operator;
import moon.compile.util.analyst.priority.OperatorPriority;

/**
 * Created by Administrator on 2016/5/11.
 */
public class ResolvedStack extends OperatorPriority {
	Stack<Character> resolvedStack = new Stack<Character>();
	private int es = 0;

	public void checkParenthesis() throws Exception {
		if (es != 0) {
			throw new Exception("expect: \'" + convert(RightParenthesis) + "\'");
		}
	}

	public Stack<Character> getResolvedStack() {
		return resolvedStack;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0, size = resolvedStack.size(); i < size; i++) {
			builder.append(resolvedStack.get(i));
		}
		return builder.toString();
	}

	public void show() {
		StringBuilder builder = new StringBuilder("[");
		for (int i = 0, size = resolvedStack.size(); i < size; i++) {
			builder.append(Operator.convert(resolvedStack.get(i)) +
			/* ": " + String.format("%#x", (int) resolvedStack.get(i)) + */", ");
		}
		builder.append("]");
		System.out.println(builder.toString());
	}

	public void add(char binCh) throws Exception {
		/**
		 *
		 */
		char preCh = resolvedStack.peek();
		if (preCh == InvokeFunction) {
			if (binCh != VAR) {
				throw new Exception("expected function pointer.");
			}
		}
		if (preCh == VAR) {
			if (resolvedStack.get(resolvedStack.size() - 2) == InvokeFunction) {
				if (binCh != LeftParenthesis) {
					throw new Exception("expect \'" + convert(LeftParenthesis) + "\' behind function invocation.");
				} else {
					resolvedStack.add(LeftParenthesis);
					es++;
					return;
				}
			}
		}

		if (binCh == LeftBracket) {
//			System.out.println(convert(preCh));
			if (preCh != VAR && preCh != Data) {
				throw new Exception("expect an operator: \'" + convert(binCh) + "\', before operand: an array variable");
			} else {
				resolvedStack.add(binCh);
				return;
			}
		}

		// if (preCh == Auxiliary) {
		// resolvedStack.add(binCh);
		// return;
		// }
		if (binCh == LeftParenthesis) {
			es++;
		}
		if (binCh == RightParenthesis) {
			if (preCh == LeftParenthesis) {

			}
			es--;
		}
		if (es < 0) {
			throw new Exception("No match: \'" + convert(LeftParenthesis)
					+ "\'");
		}

		if (binCh == Auxiliary) {
			if (isLeftAssociativityUnaryOperator(preCh) || preCh == VAR
					|| preCh == Data || preCh == Rand) {
				resolvedStack.add(binCh);
			} else {
				if (resolvedStack.get(resolvedStack.size() - 2) != FUNCTION) {
					throw new Exception("解析到表达式结尾;在运算符 \'" + convert(preCh)
							+ "\' 之后期待一个表达式.");
				} else {
					resolvedStack.add(binCh);
				}
			}
			return;
		}

		if (preCh == FUNCTION && binCh != LeftParenthesis) {
			throw new Exception("expect \'(\', behind invocation of FUNCTION.");
		}
		if (isRightAssociativityUnaryOperator(binCh)) {
			if (isLeftAssociativityUnaryOperator(preCh)) {
				throw new Exception("在单目运算符 表达式结束\'" + convert(preCh)
						+ "\' 和单目运算符表达式开始 \'" + convert(binCh)
						+ "\' 之间期待双目运算符.");
			} else if (preCh == VAR || preCh == Data || preCh == Rand) {
				throw new Exception("在操作数 \'" + convert(preCh) + "\' 和单目运算符 \'"
						+ convert(binCh) + "\' 之间期待双目运算符.");
			} else {
				resolvedStack.add(binCh);
			}
			return;
		}
		if (isLeftAssociativityUnaryOperator(binCh)) {
			if (isLeftAssociativityUnaryOperator(preCh) || preCh == VAR
					|| preCh == Data || preCh == Rand) {
				resolvedStack.add(binCh);
			} else {
				if (isRightAssociativityUnaryOperator(preCh)) {
					if (resolvedStack.get(resolvedStack.size() - 2) != FUNCTION && resolvedStack.get(resolvedStack.size() - 2) != VAR) {

						throw new Exception("在单目运算符 表达式开始 \'" + convert(preCh)
								+ "\' 和单目运算符表达式结束  \'" + convert(binCh)
								+ "\' 之间期待操作数.");
					} else {
						resolvedStack.add(binCh);
					}

				} else if (isBinaryOperator(preCh)) {
					throw new Exception("在双目运算符 \'" + convert(preCh)
							+ "\' 和单目运算符表达式结束 \'" + convert(binCh)
							+ "\' 之间期待操作数.");
				} else {
					/**
					 * is Auxiliary
					 */
					throw new Exception("在表达式开始处,单目运算符  \'" + convert(binCh)
							+ "\' 之前期待左操作数.");
				}
			}
			return;
		}
		if (isBinaryOperator(binCh)) {
			if (isLeftAssociativityUnaryOperator(preCh) || preCh == VAR
					|| preCh == Data || preCh == Rand) {
				resolvedStack.add(binCh);
			} else {
				if (binCh == Add) {
				} else if (binCh == Subtract) {
					resolvedStack.add(Negative);
				} else if (binCh == Multiply) {
					resolvedStack.add(InvokeFunction);
				} else {
					if (isRightAssociativityUnaryOperator(preCh)) {
						throw new Exception("在单目运算符 表达式开始 \'" + convert(preCh)
								+ "\' 和双目运算符\'" + convert(binCh)
								+ "\' 之间期待操作数.");
					} else if (isBinaryOperator(preCh)) {
						throw new Exception("在双目运算符 \'" + convert(preCh)
								+ "\' 和双目运算符 \'" + convert(binCh)
								+ "\' 之间期待操作数.");
					} else {
						/**
						 * is Auxiliary
						 */
						throw new Exception("在表达式开始处,双目运算符  \'"
								+ convert(binCh) + "\' 之前期待左操作数.");
					}
				}
			}
			return;
		}
		if (binCh == VAR || binCh == Data || binCh == Rand) {
			if (isLeftAssociativityUnaryOperator(preCh)) {

				throw new Exception("在单目运算符表达式结束 \'" + convert(preCh)
						+ " 和右操作数  \'" + convert(binCh) + "\' 之间期待双目运算符.");
			} else if (preCh == VAR || preCh == Data || preCh == Rand) {
				throw new Exception("在左操作数 \'" + convert(preCh) + " 和右操作数  \'"
						+ convert(binCh) + "\' 之间期待双目运算符.");
			} else {
				resolvedStack.add(binCh);
			}
			return;
		}
		/**
		 * not @,$,operator,
		 */
		throw new Exception("please contact my qq:1421053434");
	}

	public void clear() {
		es = 0;
		resolvedStack.clear();
		resolvedStack.add(Auxiliary);
	}

	public void changeEs(int mv) {
		es += mv;
	}
}
