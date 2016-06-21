package moon.compile.util;

import java.util.Stack;

import moon.compile.function.Function;
import moon.compile.function.Pointer;
import moon.compile.util.analyst.priority.OperatorPriority;
import moon.compile.util.variable.Data;
import moon.compile.util.variable.Values;
import moon.compile.util.variable.Vars;

public class Exec extends OperatorPriority {
	final Vars vars;
	final Cal cal;

	public Exec(Vars vars) {
		this.vars = vars;
		cal = new Cal(vars);
	}

	public double exec(String binS, Values values) throws Exception {
//		System.out.println(DeCompiler.deCompile(binS));
//		System.out.println(values);
		Stack<Character> resolvedStack = new Stack<Character>();
		for (int i = 0; i < binS.length(); i++) {
			resolvedStack.push(binS.charAt(i));
		}
//		JOptionPane.showMessageDialog(null,values);
//		JOptionPane.showMessageDialog(null,DeCompiler.deCompile(binS));
		return exec(resolvedStack, values);
	}

	public double exec(Stack<Character> resolvedStack, Values values)
			throws Exception {
//		JOptionPane.showMessageDialog(null, values);
		resolvedStack.remove(0);
		char currentCharacter = resolvedStack.remove(0);
		Stack<Data> operands = new Stack<Data>();
		Stack<Character> operators = new Stack<Character>();
		operators.add(Auxiliary);
		do {
//			System.out.println("values:" + operands + " : " + values);
//			JOptionPane.showMessageDialog(null, convert(currentCharacter) + "");
//			JOptionPane.showMessageDialog(null, DeCompiler.deCompile(operators) + " : " + DeCompiler.deCompile(resolvedStack));

			if (currentCharacter == Data || currentCharacter == VAR
					|| currentCharacter == Rand) {
				if (currentCharacter == Rand) {
					operands.push(new Data(Math.random()));
				} else {
					operands.push(values.remove());
					if (operands.peek() instanceof Vars.Array) {
						((Vars.Array) operands.peek()).setNonReference();
					} else if (operands.peek() instanceof Pointer) {
//						operands.push(((Pointer) operands.pop()).getPointer());
						operands.push(operands.pop());
//						if (operands.peek() instanceof Function||operands.peek().getType()==2) {
//							operators.pop();
//							operators.push(FUNCTION);
//						}
					}
//					JOptionPane.showMessageDialog(null, "stack:"+operands);
//					System.out.println("stack:" + operands);

				}
				currentCharacter = resolvedStack.remove(0);
			} else {
				/**
				 * Current character is a operator.
				 */

				/**
				 * Compare the priority.
				 */
				char operator = operators.peek();
				int r = priorityCompare(operator, currentCharacter);
				if (r == Low) {
					/**
					 * The priority top element is lower than current operator.
					 */
					if (currentCharacter == ARRAY || currentCharacter == FUNCTION) {
						operands.push(values.remove());
//						JOptionPane.showMessageDialog(null, "invoke fun: " + operands.peek());
						if (currentCharacter == FUNCTION) {

							if (resolvedStack.remove(0) != LeftParenthesis) {
								throw new Exception("unknown exception.");
							}
							((Function) operands.peek()).allocatedVar();
						}
					}
					operators.push(currentCharacter);
//					JOptionPane.showMessageDialog(null, convert(operator) + ": arg");
					currentCharacter = resolvedStack.remove(0);
				} else if (r == High) {
					/**
					 * The priority top element is Higher than current operator.
					 */
					operators.pop();
					Data value = operands.pop();
					if (isUnaryOperator(operator)) {
						if (operator == ARRAY) {
							Vars.Array array = (Vars.Array) operands.peek();
							array.setArIndex((int) value.getValue());
							continue;
						}
						if (operator == FUNCTION) {
//							JOptionPane.showMessageDialog(null, operator + ": arg" + value);
							Function f = (Function) operands.peek();
							f.pushArg(value);
							operators.push(FUNCTION);
							currentCharacter = resolvedStack.remove(0);
							continue;
						}
						if (isVarOperator(operator) || operator == New) {
							if (value.getName() == null) {
								throw new Exception(value.getValue()
										+ " is not a variable, instead of a constant.");
							}
						}
						if (operator == InvokeFunction) {
//							JOptionPane.showMessageDialog(null, operator + " : invoke" + value);
//							operands.push(cal.cal(operator, value));
//							JOptionPane.showMessageDialog(null, value);
							values.add(0, cal.cal(operator, value));
							resolvedStack.add(0, LeftParenthesis);
//							resolvedStack.add(0, FUNCTION);
							currentCharacter = FUNCTION;
//							operators.push(FUNCTION);
//							resolvedStack.insertElementAt(LeftParenthesis, 0);
//							JOptionPane.showMessageDialog(null, "next-> " + values.getFirst());

//							StringBuilder builder = new StringBuilder();
//							resolvedStack.forEach((e) ->{
//								builder.append(e);
//							});
//							JOptionPane.showMessageDialog(null, "next-> " + DeCompiler.deCompile(builder.toString()));
//							StringBuilder builder0 = new StringBuilder();
//							operators.forEach((e) ->{
//								builder0.append(e);
//							});
//							JOptionPane.showMessageDialog(null, "previous-> " + DeCompiler.deCompile(builder0.toString()));
							continue;
						}
						operands.push(cal.cal(operator, value));

					} else {
						/**
						 * Current operator is binary Operator.
						 */
//						JOptionPane.showMessageDialog(null, operands.toString() + values.toString());
						Data left = operands.pop();
						if (isVarOperator(operator) && left.getName() ==
								null) {
							throw new Exception(left.getValue() + " is not a variable, instead of a constant.");
						}
						operands.push(cal.cal(operator, left, value));
					}
				} else {
					/**
					 * The priority top element is the same with current
					 * operator.
					 */

					char previous = operators.pop();
					if (previous == FUNCTION) {
						Function function;
						moon.compile.util.variable.Data data = operands.pop();
						if (!(data instanceof Function)) {
							function = (Function) operands.pop();
							function.pushArg(data);
						} else {
							function = (Function) data;
						}
						operands.push(new Data(function.invoke()));
					}
					if (currentCharacter == RightBracket) {
						Data var0 = operands.pop();
						Data var = operands.peek();
						if (var instanceof Vars.Array) {
							//visit an array.
							var.setArIndex((int) var0.getValue());
							((Vars.Array) var).reference();
//							JOptionPane.showMessageDialog(null, resolvedStack);
//							JOptionPane.showMessageDialog(null, operands.toString() + values.toString());
						} else {
							if (var.getType() != 1) {
								throw new Exception("variable called \'" + var.getName() + "\' is not a array");
							}
							//new an array.
//							JOptionPane.showMessageDialog(null, operands.toString() + values.toString());
							var.setArrayLength((int) var0.getValue());
//							JOptionPane.showMessageDialog(null, resolvedStack);
//							JOptionPane.showMessageDialog(null, operands.toString() + values.toString());
					}
					}
					if (currentCharacter == Auxiliary) {
						/**
						 * Now the top element is '#'.
						 */
						if (operands.peek().getType() == 2) {
							Data data = operands.pop();
							Function function = vars.getFunctionCalled(data.getName());
							operands.push(function);
						}
						break;
					}
					currentCharacter = resolvedStack.remove(0);

				}
			}
		} while (true);
		if (operands.size() != 1) {
//			JOptionPane.showMessageDialog(null, operands);
			throw new Exception("illegal expression");
		}
		return operands.pop().getValue();
	}
}
