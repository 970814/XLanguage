package moon.compile.function;


import moon.Moon;
import moon.compile.sourcecode.SourceCode;
import moon.compile.util.variable.Var;
import moon.mechanism.ReturnMechanism;
import moon.compile.util.analyst.constant.ReservedWords;
import moon.compile.util.analyst.priority.Operator;
import moon.compile.util.variable.Data;
import moon.compile.util.variable.Vars;
import moon.compile.CompileStack;
import moon.compile.SourceCodeAnalyst;
import moon.compile.sourcecode.CodeSet;
import moon.parse.interpreter.Interpreter;

import java.io.PrintStream;
import java.util.*;


public class Function extends Data {

	private boolean isNative = false;
	private boolean isVariableParameter = false;

	public Function(String name) throws Exception {
		super(name);
	}

	Stack<Vars> varsStack = new Stack<>();

	public static void setStringQueue(LinkedList<String> stringQueue) {
		Function.stringQueue = stringQueue;
	}

	public static void setInputStringBuilder(Queue<String> inputStringBuilder) {
		Function.inputStringBuilder = inputStringBuilder;
		if (inputStringBuilder == null) {
			scanner = new Scanner(System.in);
		}
	}

	public static Queue<String> getInputStringBuilder() {
		return inputStringBuilder;
	}

	public void allocatedVar() {
		varsStack.push(new Vars());
	}

	private boolean isLinked = false;

	public void functionLinked(CompileStack compileStack, Queue<StringBuilder> codes) throws Exception {
		if (isLinked) {
			throw new Exception("function called: \'" + name + "\' has already defined.");
		}
		sourceCodeAnalyst.nonDefineFunction();
		try {
			body = sourceCodeAnalyst.compile(compileStack, codes);
		} catch (Exception e) {
			throw new Exception("Error source code grammar: in function called: \'" + name + "\' :" + e.getMessage());
		}
		isLinked = true;
	}

	public boolean isLinked() {
		return isLinked;
	}

	private final Vars args = new Vars();
	private SourceCodeAnalyst sourceCodeAnalyst = new SourceCodeAnalyst();
	private CodeSet body;
	private Vector<Function> functionSet;


	public void defineArgs(String vs) throws Exception {

		if (isVariableParameter) {
			throw new Exception("Variable parameter function can not has more parameters.");
		}
		vs = vs.trim();
		if (vs.length() == 0) {
			throw new Exception("problem: parameter expected.");
		}
		if (vs.endsWith("[]")) {
			args.addAr(vs.substring(0, vs.length() - 2).trim(), 0);
		} else if (vs.startsWith("*")) {
			args.newVar(new FPointer(vs.substring(1, vs.length()).trim()));
		} else if (vs.startsWith("&")) {
			args.newVar(new Pointer(vs.substring(1, vs.length()).trim()));
		} else if (vs.startsWith("...")) {
//			args = null;
			isVariableParameter = true;
			args.newVar(new Var(vs.substring(3, vs.length()).trim()));
		} else {
			args.newVar(new Data(vs));
		}

	}

	public static Function defineFunction(StringBuilder code) throws Exception {

		if (code.toString().length() == 0) {
			throw new Exception("Function definition expected.");
		}
		if (!code.toString().endsWith(")")) {
			throw new Exception("Define function error:expected \')\'");
		}
		boolean isNative = false;
		if (code.toString().startsWith("native ")) {
			code.delete(0, 7);
			isNative = true;
		}
		StringBuilder v = new StringBuilder();
		int i = -1;
		char[] chs = new char[code.length()];
		Operator.toCharArray(code.toString(), chs);
		int size = chs.length;
		boolean isLast = false;
		while (++i < size && chs[i] != '(') {
			if (!isLast && chs[i] == ' ') {
				continue;
			}
			isLast = true;
			v.append(chs[i]);
		}
		if (i >= size) {
			throw new Exception("Arguments definition expected.");
		}
		if (v.length() == 0) {
			throw new Exception("function name definition expected.");
		}
		ReservedWords.checkName(v.toString());
		Function function = new Function(v.toString());
		try {
			StringBuilder args = new StringBuilder(code.substring(i + 1, code.length() - 1));
			if (args.length() != 0) {
				v.setLength(0);
				size = args.length();
				i = -1;
				boolean isExpect = false;
				while (++i < size) {
					if (args.charAt(i) == ' ') {
						continue;
					}
					while (i < size && args.charAt(i) != ',') {
						isExpect = false;
						v.append(args.charAt(i));
						++i;
						if (args.charAt(i - 1) == '[') {
							while (i < size && args.charAt(i) == ' ') {
								++i;
							}
						}
					}
					if (i < size) {
						if (args.charAt(i) == ',') {
							if (isExpect) {
								throw new Exception("problem: between \',\' and \',\': parameter expected.");
							}
							isExpect = true;
						}
					}
//					JOptionPane.showMessageDialog(null, "newVar: "+v);
					function.defineArgs(v.toString());

					v.setLength(0);
				}
				if (isExpect) {
					throw new Exception("Function definition error: problem: parameter expected.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("For Function: \'" + function.getName() + "\': " + e.getMessage());
		}
//		JOptionPane.showMessageDialog(null, function.getArgs() +" : "+ function.getArgs().hashCode());
		function.setToNative(isNative);
		return function;
	}

	public Vars getArgs() {
		return args;
	}

	@Override
	public String toString() {
//		return toString0();
		return getName() + "(" + new StringBuilder(args.toString()).deleteCharAt(args.toString().length() - 1).deleteCharAt(0) + ")";
	}

	public String toString0() {
		StringBuilder s = new StringBuilder(getName());
		s.append('(');
		int i = 0;
		for (int size = args.size(); i < size; i++) {
			moon.compile.util.variable.Data data = args.get(i);
			String arg = args.get(i).getName();
			if (data instanceof Vars.Array) {
				arg += "[]";
			}
			s.append(arg + ", ");

		}
		if (i > 0) {
			s.delete(s.length() - 2, s.length());
		}
		s.append(')');
		return s.toString();
	}

	private double run() throws Exception {
		if (!isLinked) {
			throw new Exception("function called \'" + name + "\' has not been defined.");
		}

		Vars vars;
		if (varsStack.size() == 0) {
			vars = new Vars();
			for (Data data : args) {
				if (data instanceof Vars.Array) {
					vars.addAr(data.getName(), 0);
				} else if (data instanceof FPointer) {
					vars.newVar(new FPointer(data.getName()));
				} else if (data instanceof Var) {
					vars.newVar(new Var(data.getName()));
				} else {
					vars.newVar(new Data(data.getName()));
				}
			}
		} else {
			vars = varsStack.pop();
			if (vars.size() != args.size()) {
//				JOptionPane.showMessageDialog(null, args.size());
//				JOptionPane.showMessageDialog(null, vars.size());
				throw new Exception("invoke function called: \'" + name + "\' ;expect: " + args.size() + " argument" + (args.size() > 0 ? "s" : ""));
			}
		}


		for (Function function : functionSet) {
			vars.add(function);
		}
//		Function callSelf = (Function) clone();
//		return callSelf.run(new Interpreter());
		Interpreter interpreter = new Interpreter(vars);
		vars = null;
		return run(interpreter);
	}

	static int i = 0;

	private double run(Interpreter interpreter) throws Exception {
//		System.out.println(name + ": start: " + interpreter.getVars());
		double returnValue;
		try {
			returnValue = body.interpretAll(interpreter);
		} catch (ReturnMechanism returnMechanism) {
			return returnMechanism.getReturnValue();
		}
//		System.out.println(name + ": end: " + interpreter.getVars());
		return returnValue;
	}

	static transient Queue<String> stringQueue = new LinkedList<>();
	static transient Queue<String> inputStringBuilder = new LinkedList<>();
	static transient Scanner scanner = null;
	public static Queue<String> getStringQueue() {
		return stringQueue;
	}

	public void setAccessRights(Vector<Function> functionSet) {
		this.functionSet = functionSet;
	}

	public static Long startTime = null;

	public double invoke(Data... datas) throws Exception {
//		if (datas.length < args.size()) {
//			throw new Exception("invoke function called \'" + name + "\' :" + "expect ");
//		}
		allocatedVar();
		int i = 0;
		for (int size = args.size(); i < size && i < datas.length; i++) {
			moon.compile.util.variable.Data data = args.get(i);
			if (data instanceof Pointer) {
				((Pointer) data).setPointer(new Data(datas[i].getValue()));
			} else if (data instanceof FPointer) {
				((FPointer) data).reference(datas[i].getName());
			} else if (data instanceof Vars.Array) {
				data = new Vars.Array(data.getName(), (int) datas[i].getValue());
			} else if (data instanceof Var) {
				break;
			} else {
				data = new Data(data.getName());
				data.setValue(datas[i].getValue());
			}
			varsStack.peek().add(data);
		}
		if (i < args.size() && i < datas.length) {
			if (i != args.size() - 1) {
				throw new Exception("invoke function called \'" + name + "\' :" + "Variable can't have more argument.");
			}
			Var var = new Var(args.getLast().getName());
			for (; i < datas.length; i++) {
				var.add(datas[i]);
			}
			varsStack.peek().add(var);
		}
		return invoke();
	}

	public double invoke() throws Exception {
//		if (!getName().equals("arrayElementSwap")) {
//			JOptionPane.showMessageDialog(null, "swap");
//		}
		if (startTime == null) {
			startTime = System.currentTimeMillis();

			if (Moon.getOut() != null) {
				System.setOut(new PrintStream(Moon.getOutFile()));
			}
		}
		try {
			if (isNative()) {
				if (getName().equals("print")) {
					Vars vars = varsStack.pop();
					print:

					if (this.stringQueue != null) {
						stringQueue.add(vars.get(0).getValue() + ", ");
					} else {
						System.out.print(vars.get(0).getValue() + ", ");
					}


//				for (xlanguage.compile.util.variable.Data var : vars) {
//					if (var instanceof Pointer) {
//						var = ((Pointer) var).getPointer();
//					}
//					if (var instanceof Vars.Array) {
//						JOptionPane.showMessageDialog(null, "out: " + Arrays.toString(((Vars.Array) var).getArray()));
//					} else if (var instanceof Var) {
//						for (xlanguage.compile.util.variable.Data data : ((Var) var).getArgs()) {
//							i++;
//							JOptionPane.showMessageDialog(null, "out: " + data);
//						}
//						break print;
//					} else {
//						JOptionPane.showMessageDialog(null, "out: " + var);
//					}
//					i++;
//				}
					return 1;
				}
				if (getName().equals("newline")) {
//				JOptionPane.showMessageDialog(null, "\n");

					if (this.stringQueue != null) {
						stringQueue.add("\n");
					} else {
						System.out.println();
					}

					return 0;
				}
				if (getName().equals("memorycopy")) {
					Vars vars = varsStack.pop();
					double[] src = ((Vars.Array) vars.get(0)).getArray();
					int srcPos = (int) vars.get(1).getValue();
					double[] dest = ((Vars.Array) vars.get(2)).getArray();
					int destPos = (int) vars.get(3).getValue();
					int length = (int) vars.get(4).getValue();
					System.arraycopy(src, srcPos, dest, destPos, length);
					return 0;
				}
				if (getName().equals("scan")) {
					Double v = null;
					while (v == null) {
						try {
							String input;
							if (inputStringBuilder != null) {
								while (inputStringBuilder.size() == 0) {
									Thread.sleep(8);
								}
								input = inputStringBuilder.remove();
							} else {
								input = scanner.nextLine();
							}
							v = Double.parseDouble(input);
						} catch (NoSuchElementException e) {
							e.printStackTrace();
//							throw new Exception(e);
							break;
						} catch (Exception e) {
							String s = e.getMessage() + ": is not a digit.\n";
							if (this.stringQueue != null) {
								stringQueue.add(s);
							} else {
								System.out.print(s);
							}
						}
					}

					return v;
				}
				if (getName().equals("printraw")) {
					Vars vars = varsStack.pop();
					String s = ((Pointer) vars.get(0)).getPointer().getName().replace("_", " ");
					if (this.stringQueue != null) {
						stringQueue.add(s);
					} else {
						System.out.print(s);
					}
					return 1.0;
				}

				if (getName().equals("currenttime")) {
					return ((int) ((System.currentTimeMillis() - startTime) / 10)) / 100.0;//SEC
				}

				if (getName().equals("printar")) {
					Vars vars = varsStack.pop();
					print:
//				JOptionPane.showMessageDialog(null, Arrays.toString(((Vars.Array) vars.get(0)).getArray()));

					if (this.stringQueue != null) {
						stringQueue.add(Arrays.toString(((Vars.Array) vars.get(0)).getArray()) + "\n");
					} else {
						System.out.println(Arrays.toString(((Vars.Array) vars.get(0)).getArray()));
					}

					return 1;
				}
//			for (Function function : functionSet) {
//				varsStack.peek().add(function);
//
//			}
			}
		} catch (Exception e) {
			throw new Exception("Native function expect real argument.");
		}
		try {
			return run();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("invoke function called: \'" + name + "\' : " + e.getMessage());
		}
	}

	public void pushArg(Data value) throws Exception {
//		JOptionPane.showMessageDialog(null, value);
		if (value instanceof Var) {
			throw new Exception("variable parameter can not be accepted.");
		}
		if (value instanceof Pointer) {
			Pointer pl = (Pointer) value;
//			if (((Pointer) value).getPointer() instanceof Vars.Array && !((Vars.Array) ((Pointer) value).getPointer()).isReference()) {
//				value = pl.getPointer();
//			} else if (((Pointer) value).getPointer() instanceof FPointer) {
//				value = pl.getPointer();
//			} else  {
				value = pl.getPointer();
//			}
		}
		Vars vars = varsStack.peek();
		moon.compile.util.variable.Data newArg;
		try {
			int index = vars.size();
			if (vars.size() >= args.size() && !isVariableParameter) {
				throw new Exception(" expected: " + args.size() + " argument" + (args.size() > 0 ? "s" : ""));
			} else if (vars.size() == args.size() - 1 && isVariableParameter) {
				Var var = new Var(args.getLast().getName());
				var.add(value);
				vars.add(var);
				return;
			} else if (vars.size() == args.size() && isVariableParameter) {
				((Var) vars.getLast()).add(value);
				return;
			}
			newArg = args.get(index);
			String arg = newArg.getName();


			if (newArg instanceof Pointer) {
				if (value.getName() == null) {
					throw new Exception("pointer expect a variable.");
				} else {
					if (value instanceof Pointer) {
						value = ((Pointer) value).getPointer();
					}
					((Pointer) newArg).setPointer(value);
				}
			} else if (newArg instanceof Vars.Array) {
				if (!(value instanceof Vars.Array) || ((Vars.Array) value).isReference()) {
					throw new Exception("For the " + vars.size() + " argument, expect: Array Type.");
				}
				newArg = new Vars.Array(arg, 0);
				((Vars.Array) newArg).setArray(((Vars.Array) value).getArray());
			} else if (newArg instanceof FPointer) {
				try {
					if (value.getName() == null) {
						throw new Exception("constant: " + value.getValue() + " is not usage.");
					} else if (value instanceof Vars.Array) {
						throw new Exception("array: " + value.getName() + " is not usage.");
					}
				} catch (Exception e) {
					throw new Exception("For the " + vars.size() + " argument, expect: function pointer, " + e.getMessage());
				}
//				JOptionPane.showMessageDialog(null, vars);
				newArg = new FPointer(arg);
				if (value instanceof FPointer) {
					((FPointer) newArg).reference(((FPointer) value).getPointer());
				}else {

					((FPointer) newArg).reference(value.getName());
				}
			} else {
				if (value instanceof Vars.Array && !((Vars.Array) value).isReference()) {
					throw new Exception("For the " + vars.size() + " argument, Array Type cannot be applied to Var Type.");
				}
				newArg = new Data(arg);
				newArg.setValue(value.getValue());
			}

		} catch (Exception e) {
			throw new Exception("invoke function called \'" + name + "\' :" + e.getMessage());
		}

		vars.add(newArg);
	}

	@Override
	public double getValue() throws Exception {
		return hashCode();
	}

	@Override
	public void setValue(double value) throws Exception {
		throw new Exception("Function cannot be lvalue.");
	}

	public void setToNative(boolean toNative) {
		this.isNative = toNative;
	}

	public boolean isNative() {
		return isNative;
	}


	public StringBuilder getSourceCode() {
		if (!isLinked()) {
			return null;
		}
		StringBuilder builder = new StringBuilder(toString());
		getSourceCode(builder);
		return builder;
	}

	private void getSourceCode(StringBuilder builder) {
		for (SourceCode sourceCode : body) {
			String code = sourceCode.getSourceCode();
			if (code != null) {
				builder.append(code + ";\n");
			} else {
				getSourceCode(builder);
			}
		}
	}


}
