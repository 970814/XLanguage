package moon.compile.util.variable;

import moon.compile.util.analyst.constant.BaseDataConstant;
import moon.compile.util.analyst.constant.KeyWord;

import java.util.Stack;

/**
 * Created by Administrator on 2016/5/10.
 */
public class Data implements BaseDataConstant {
	protected String name;
	protected double value;
	/**
	 * The index mark a var.
	 */
	protected int arIndex = -1;

	boolean isNewVar;

	public boolean isNewVar() {
		return isNewVar;
	}

	public Data(double value) {

		this.value = value;
	}

	private Data(String name, double value, boolean recheck) throws Exception {
		this.name = name;
		this.value = value;
		if (recheck) {
			isValid(name);
		}else if (isDefined(name)) {
			throw new Exception("\'" + name + "\' is a constant name or a operator name.");
		}
		isNewVar = !recheck;
	}

	public Data(String name) throws Exception {
		this(name, 0.0, true);
	}

	public Data(String name, boolean recheck) throws Exception {
		this(name, 0.0, recheck);
	}


	public String getName() {
		return name;
	}

	public double getValue() throws Exception {
		if (type == 2) {
			return hashCode();
		}
		return value;
	}

	public void setValue(double value) throws Exception {
		if (type == 2) {
			throw new Exception("function cannot be assign.");
		}
		this.value = value;
	}

	/**
	 * Visit an array use this index.
	 *
	 * @param arIndex
	 * The index of an array.
	 */
	protected Stack<Integer> arrayIndex = new Stack<>();
	public void setArIndex(int arIndex) throws Exception {
		if (!(this instanceof Vars.Array)) {
			throw new Exception(name + " is not an array");
		}
		this.arIndex = arIndex;
		arrayIndex.push(arIndex);
	}

	public void clear() {
		arrayIndex = new Stack<>();
	}

	public int getArIndex() throws Exception {
		if (!(this instanceof Vars.Array)) {
			throw new Exception(name + " is not an array");
		}
		if (arrayIndex.size() > 0) {
			arIndex = arrayIndex.pop();
		}
		return arIndex;
	}

	@Override
	public String toString() {
		return "T" + type +";"+ name + " = " + value;
	}

	private void isValid(String name) throws Exception {
		if (name == null) {
			return;
		}
		if (!isValidIdentifier(name)) {
			throw new Exception("\'" + name + "\' is not a valid identifier");
		}
		if (isDefined(name)) {
			throw new Exception("\'" + name + "\' is a defined name");
		}
	}

	public static boolean isValidIdentifier(String name) {
		if (name.length() <= 0) {
			return false;
		}
		boolean isValid = true;
		if (Character.isDigit(name.charAt(0))) {
			isValid = false;
		}
		if (isValid) {
			for (int i = 1; i < name.length(); i++) {
				if (!Character.isDigit(name.charAt(i))
						&& !Character.isAlphabetic(name.charAt(i))
						&& name.charAt(i) != '_' /*&& name.charAt(i) != '$'*/) {

					isValid = false;
				}
			}
		}
		if (!isValid) {
			return false;
		}
		return true;
	}

	public static boolean isDefined(String name) {
		if (KeyWord.getBinOP(name) != null) {
			return true;
		}
		for (int i = 0; i < constantName.length; i++) {
			for (int j = 0; j < constantName[i].length; j++) {
				if (name.equals(constantName[i][j])) {
					return true;
				}
			}
		}
		return false;
	}

	int type = 0;

	/**
	 * -1 constant.
	 * 0 var.
	 * 1 array.
	 * 2 function.
	 */

	public void setToArray() {
		type = 1;
	}

	public int getType() {
		return type;
	}

	int arrayLength = -1;

	public void setArrayLength(int value) {
		arrayLength = value;
	}

	public int getArrayLength() {
		return arrayLength;
	}

	public void setType(int type) {
		this.type = type;
	}
}
