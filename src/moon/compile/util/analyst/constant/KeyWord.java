package moon.compile.util.analyst.constant;

import java.util.Vector;

/**
 * Created by Administrator on 2016/5/12.
 */
public class KeyWord implements BaseDataConstant {

	final public static Vector<String[]> STRINGS = new Vector<>();

	// final public static String allOperators = new String(AllOperators);
	static {
		add("" + Auxiliary);
		add("add", "+");
		add("subtract", "sub", "-");
		add("multiply", "mul", "*");
		add("divide", "div", "/");
		add("power", "pow", "^");
		add("%");
		add("sqrt");
		add("abs");
		add("(");
		add(")");
		add("negative", "neg", "~");
		add("!");
		add("aqrt", ":");
		add("modulo", "mod", "|");
		add("arcsin");
		add("arccos");
		add("arctan");
		add("sin");
		add("cos");
		add("tan");
		add("ln");
		add("log");
		add("degree", "deg");
		add("radian", "rad");
		add("random");
		add("rand");
		add("bigger", ">");
		add("smaller", "<");
		add("same", "==");
		add("or", "||");
		add("and", "&&");
		add("not");
		add("assign", "=");
		add("comma", ",");
		add("inc", "++");
		add("dec", "--");
		add("int");
		add("bs", ">=");
		add("ls", "<=");
		add("+=");
		add("-=");
		add("*=");
		add("/=");
		add("|=");
		add("!=");
		add("sizeof");
		add("new");
		add("ARRAY");
		add("FUNCTION");
		add("[");
		add("]");
		add("" + InvokeFunction + "*");
		add("get");
	}

	private static void add(String... args) {
		STRINGS.add(args);
	}

	/**
	 * If the op is exist, than return binary op, else return null.
	 */
	public static Character getBinOP(String op) {
		for (int i = 0, size = STRINGS.size() ; i < size; i++) {

			String[] data = STRINGS.get(i);
			for (int j = 0; j < data.length; j++) {
				if (data[j].equals(op)) {
					return AllOperators[i];
				}
			}
		}
		return null;
	}

	public static class Data {
		String[] names;
		Character op;

		public Data(String... names) {
			this.names = names;
		}

		public void setOp(char op) {
			this.op = op;
		}
	}

}
