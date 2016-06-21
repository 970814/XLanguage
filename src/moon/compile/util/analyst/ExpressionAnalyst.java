package moon.compile.util.analyst;

import moon.compile.function.FPointer;
import moon.compile.function.Function;
import moon.compile.util.analyst.constant.KeyWord;
import moon.compile.util.analyst.constant.ReservedWords;
import moon.compile.util.analyst.priority.Operator;
import moon.compile.util.variable.Data;
import moon.compile.util.variable.Values;
import moon.compile.util.variable.Vars;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/5/10.
 */
// ~(e*3.0%*pi)aqrt((~sqrt((sqrt(~sqrt(~(~345*(45.0+21.0)/8.8)+(~sqrt
// 3.%-.12^44.12%))/3*~4.0^2.))+.123)!+pi^~e)^2)^e*pi%
// [#, negative, (, $, power, $, add, $, subtract, $, ), aqrt, sqrt, (, $,
// divide, $, multiply, sqrt, (, (, $, subtract, (, $, add, $, ), %, ), divide,
// negative, $, ), %, multiply, $, divide, (, sqrt, (, $, multiply, $, power, $,
// ), ), %, ), #, ]
public class ExpressionAnalyst extends Operator {

	/*
	 * public void analysis(String executeExpression) throws Exception {
	 */

	final Vars vars;
	final Values values;
	final ResolvedStack resolvedStack;

	public ExpressionAnalyst(Vars vars, Values values,
							 ResolvedStack resolvedStack) {
		this.vars = vars;
		this.values = values;
		this.resolvedStack = resolvedStack;
	}

	public void showResult() {
		resolvedStack.show();
		System.out.println(Arrays.toString(values.toArray()));
	}

	/**
	 * Analysis a input/standard expression.
	 */
	public void analysis(String expStr) throws Exception {
		int i = -1;
//		expStr += Auxiliary;
		values.clear();
		resolvedStack.clear();
		final int size = expStr.length();
		char[] exp = new char[size];
		toCharArray(expStr, exp);

		StringBuilder v;
		try {
			while (++i < size) {
				if (exp[i] == ' ') {
					continue;
				}
				/**
				 * If is a double number.
				 */
				if (Character.isDigit(exp[i]) || exp[i] == '.') {
					/**
					 * adding a literal.
					 */
					v = new StringBuilder("" + exp[i]);
					for (++i; i < size; i++) {
						if (Character.isDigit(exp[i]) || exp[i] == '.') {
							v.append(exp[i]);
						} else {
							break;
						}
					}
					try {
						/**
						 * adding a literal.
						 */
						values.add(Double.parseDouble(v.toString()));
					} catch (Exception e) {
						/**
						 * It is illegal.
						 */
						throw new Exception("\'" + v
								+ "\' is not a valid number.");
					}
					i--;
					resolvedStack.add(Data);
					continue;
				}
				if (isSplitCharacter(exp[i])) {
					final char currentCh = exp[i];
					String op = "" + currentCh;
					if (i >= size - 1) {
						resolvedStack.add(KeyWord.getBinOP("" + currentCh));
						break;
					}
					final char ch = exp[i + 1];

					if ("+-=|&".indexOf(currentCh) != -1 && currentCh == ch) {
						/**
						 * ++,--,==,||,&&,
						 */
						i++;
						op = "" + ch + ch;
					}
					if ("+-*/|><!".indexOf(currentCh) != -1 && ch == '=') {
						/**
						 * +=,-=,*=,/=,|=,>=,<=,!=,
						 */
						i++;
						op = "" + currentCh + ch;
					}
					Character binOP = KeyWord.getBinOP(op);
//					if (binOP == LeftParenthesis) {
//						if (values.size() > 0 && resolvedStack.resolvedStack.peek() == FUNCTION) {
//							String name = values.getLast().getName();
//							Function function = vars.getFunctionCalled(name);
//							if (function != null) {
//								resolvedStack.resolvedStack.pop();
//								resolvedStack.resolvedStack.add(FUNCTION);
//								values.removeLast();
//								values.add(function);
//							}
////							} else  {
////
////								if (!(values.getLast() instanceof Vars.Array) && !isRightAssociativityUnaryOperator(resolvedStack.resolvedStack.peek())) {
////									throw new Exception("variable called: \'" + name + "\' is not a function");
////								}
////
////							}
//						}
//					}
					char previous = resolvedStack.resolvedStack.peek();

					if (binOP == LeftParenthesis && values.size() > 0 && (previous == Data || previous == VAR)) {
//						JOptionPane.showMessageDialog(null, values.getLast());
						Data var = values.getLast();
//						if (var instanceof Pointer) {
////							resolvedStack.resolvedStack.pop();
////							resolvedStack.resolvedStack.add(VAR);
//							JOptionPane.showMessageDialog(null, var);
//							values.removeLast();
//							compile.util.variable.Data data = ((Pointer) var).getPointer();
//							if (data instanceof Function || data.getType() == 2) {
//								values.add(vars.getFunctionCalled(data.getName()));
//							}
//
//
//							resolvedStack.resolvedStack.add(LeftParenthesis);
//							resolvedStack.changeEs(1);
//							continue;
//						}

						if (var instanceof FPointer && resolvedStack.resolvedStack.get(resolvedStack.resolvedStack.size() - 2) == InvokeFunction) {


							resolvedStack.resolvedStack.pop();
							resolvedStack.resolvedStack.add(VAR);
							resolvedStack.resolvedStack.add(LeftParenthesis);
							resolvedStack.changeEs(1);
							continue;
						}
//						JOptionPane.showMessageDialog(null, var);
						if (!(var instanceof Function)) {
							Function function = vars.getFunctionCalled(var.getName());
							if (function != null) {
								resolvedStack.resolvedStack.pop();
								resolvedStack.resolvedStack.add(FUNCTION);
								values.removeLast();
								values.add(function);
							}
						}
					}


					if (binOP == LeftBracket && values.size() > 0) {
						Data var = values.getLast();
						if (var instanceof Vars.Array) {
							//visit a array.
							((Vars.Array) var).reference();
//							resolvedStack.resolvedStack.pop();
//							resolvedStack.resolvedStack.push(VAR);
						} else {
							//new a array.
							if (resolvedStack.resolvedStack.size() < 2 || resolvedStack.resolvedStack.get(resolvedStack.resolvedStack.size() - 2) != New) {
								throw new Exception("\'" + var + "\' might be not an array");
							}
							var.setToArray();
						}
						resolvedStack.add(binOP);
						continue;
					}
					resolvedStack.add(binOP);
					continue;
				}
				if (Character.isAlphabetic(exp[i]) || exp[i] == '_') {
					v = new StringBuilder();
					v.append(exp[i]);
					boolean isNewVar = false;
					if (resolvedStack.getResolvedStack().peek() == New) {
						isNewVar = true;
					} /*else if (resolvedStack.getResolvedStack().peek() == ARRAY) {
						resolvedStack.getResolvedStack().pop();
						resolvedStack.getResolvedStack().push(VAR);
						((Vars.Array) values.getLast()).setNonReference();
						continue;
					}*/ /*else if (resolvedStack.getResolvedStack().peek() != New) {
						*//*exp[i] == '$'*//*
						throw new Exception("before \'$\': expect: " + convert(New));
					}*/
					for (++i; i < size; i++) {
						if (Character.isAlphabetic(exp[i]) || exp[i] == '_'
								|| Character.isDigit(exp[i])) {
							v.append(exp[i]);
						} else {
							break;
						}
					}
					i--;
					final String name = v.toString();
					ReservedWords.checkName(name);
					int index = vars.getIndex(name);// get the index of var.
					if (index == -1 && !isNewVar) {
						Character binOP = KeyWord.getBinOP(name);
						if (binOP == null) {
							/**
							 * it is only a constant.
							 */
							boolean isDefined = false;
							loop:
							for (int m = 0; m < constantName.length; m++) {
								for (int n = 0; n < constantName[m].length; n++) {
									if (constantName[m][n].equals(name)) {
										values.add(constantValue[m]);
										resolvedStack.add(Data);
										isDefined = true;
										break loop;
									}
								}
							}
							if (isDefined) {
								continue;
							}
							throw new Exception("\'" + v
									+ "' is neither a variable nor an operator");
						} else {
							/**
							 * it is a operator.
							 */
							resolvedStack.add(binOP);
							continue;
						}
					} else {
						/**
						 * v is a var, then add a it. it might be a var or
						 * array.
						 */
						if (isNewVar) {
//							while (++i < size && exp[i] == ' ') {
//							}
//							if (i < size && exp[i] == '[') {
//								v.append(exp[i]);
//								while (++i < size && exp[i] != ']') {
//									v.append(exp[i]);
//								}
//								if (i >= size) {
//									throw new Exception(
//											"expect character: \'" + ']' + '\'');
//								}
//								v.append(exp[i]);
//							} else {
//								i--;
//							}

//							if (vars.getFunctionCalled(name) == null && index != -1) {
//								throw new Exception("The variable called: \'" + v.toString() + "\' has already existed.");
//							}
							values.add(new Data(v.toString(), false));
							resolvedStack.add(Data);
							continue;
						}
						if (vars.isArray(index)) {
							/**
							 * adding a value of array.
							 */
//							resolvedStack.add(ARRAY);
//							resolvedStack.add(VAR);
							((Vars.Array) vars.get(index)).setNonReference();
							resolvedStack.add(VAR);
						} else if (vars.isFunction(index)) {
							String var = vars.get(index).getName();
							index = vars.getIndex(var);
							if (vars.isFunction(index)) {
								resolvedStack.add(VAR);
								moon.compile.util.variable.Data data = new Data(var);
								data.setType(2);
								values.add(data);
								continue;
							}else {
								resolvedStack.add(VAR);
							}
//							resolvedStack.add(VAR);
						} else {
							resolvedStack.add(VAR);
						}

						// the most important code.
						values.add(index);
						continue;
					}
				}
				throw new Exception("unknown character: \'" + exp[i] + "\': "
						+ String.format("%#x", (int) exp[i]));
			}
//			JOptionPane.showMessageDialog(null, values);
			resolvedStack.add(Auxiliary);
			resolvedStack.checkParenthesis();
			StringBuilder builder = new StringBuilder();
			resolvedStack.resolvedStack.forEach((e) ->{
				builder.append(e);
			});
//			JOptionPane.showMessageDialog(null, DeCompiler.deCompile(builder.toString()));
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("编译异常: 语法错误: 解析到第" + (i + 1)
					+ "个字符出现异常; problem: " + e.getMessage());
		}

	}

	private boolean isSplitCharacter(char op) {
		return operators.indexOf(op) != -1;
	}

}
