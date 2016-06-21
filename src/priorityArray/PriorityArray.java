package priorityArray;

import java.util.Vector;

import moon.compile.util.analyst.constant.KeyWord;
import moon.compile.util.analyst.priority.OperatorPriority;

public class PriorityArray extends OperatorPriority {
	static final String HIGH = "High";
	static final String LOW = "Low";
	static final String EQUAL = "Equal";
	static final String UNDEFINED = "None";

	public static String[][] operatorPriority() {
		Vector<String[]> strs = KeyWord.STRINGS;
		String[] strings = new String[strs.size() + 1];
		strings[0] = "";
		for (int i = 0, size = strs.size(); i < size; i++) {
			String[] s = strs.get(i);
			strings[i + 1] = s[s.length - 1];/* Arrays.toString(s); */
		}
		String[][] result = new String[AllOperators.length + 1][AllOperators.length + 1];
		result[0] = strings;
		for (int i = 1; i < result.length; i++) {
			result[i][0] = strings[i];
			for (int j = 1; j < result[i].length; j++) {
				result[i][j] = outResult(AllOperators[i - 1], AllOperators[j - 1]);
			}
		}
		return result;
	}

	private static String outResult(char op1, char op2) {

		String s = UNDEFINED;
		try {
			int r = OperatorPriority.priorityCompare(op1, op2);
			if (r == High) {
				s = HIGH;
			}
			if (r == Low) {
				s = LOW;
			}
			if (r == Equal) {
				s = EQUAL;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return s;
	}
}
