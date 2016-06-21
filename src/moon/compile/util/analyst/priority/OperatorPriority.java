package moon.compile.util.analyst.priority;

/**
 * Created by Administrator on 2016/5/10.
 */
public class OperatorPriority extends Operator {
	public OperatorPriority() {
	}

	private static int getBinaryPriority(char op) throws Exception {
		if (new String(new char[] { Power, AnyThPowerRoot, }).indexOf(op) != -1) {
			return 5;
		}
		if (new String(new char[] { Multiply, Divide, Modulo, }).indexOf(op) != -1) {
			return 4;
		}
		if (new String(new char[] { Add, Subtract, }).indexOf(op) != -1) {
			return 3;
		}
		if (new String(new char[]{Bigger, Smaller, Same, BiggerOrSame,
				LowerOrSame, NotEqual}).indexOf(op) != -1) {
			return 2;
		}
		if (new String(new char[] { Or, And, }).indexOf(op) != -1) {
			return 1;
		}
		if (new String(new char[] { Assign, AddAssign, SubAssign, MulAssign,
				DivAssign, ModAssign, }).indexOf(op) != -1) {
			return 0;
		}
		if (op == Comma) {
			return -1;
		}
		throw new Exception("expect binary: " + String.format("%#Moon", (int) op));
	}

	static int i = 0;
	public static int priorityCompare(char op1, char op2) throws Exception {
		if (op1 == InvokeFunction) {
			return High;
		}

		if (op1 == FUNCTION) {
//			System.out.println(++i);
//			System.out.println(convert(op1) + " cmp " + convert(op2));
			if (op2 == Comma) {
				return High;
			}
			if (op2 == RightParenthesis) {
//				System.out.println(++i);
				return Equal;
			}
			return Low;
		}
		if (op1 == Rand) {
			/**
			 * quick make an random number.
			 */
			return High;
		}
		if (op2 == Rand) {
			return Low;
		}
		if (isLeftAssociativityUnaryOperator(op1)
				&& isRightAssociativityUnaryOperator(op2)) {
			throw new Exception("expect binary operator between \'"
					+ String.format("%#Moon", (int) op1) + "\' and \'"
					+ String.format("%#Moon", (int) op2) + "\'");
		}
		if (op1 == Auxiliary && op2 == Auxiliary) {
			return Equal;
		}
		if (op1 == LeftParenthesis && op2 == RightParenthesis) {
			return Equal;
		}
		if (op1 == LeftBracket && op2 == RightBracket) {
			return Equal;
		}
		if (isBinaryOperator(op1)) {
			if (isUnaryOperator(op2)) {
				/**
				 * Binary and Unary For Priority, binary operator is Smaller
				 * than the unary operator.
				 */
				return Low;
			} else if (isBinaryOperator(op2)) {
				/**
				 * Binary and Binary
				 */
				return getBinaryPriority(op1) - getBinaryPriority(op2) >= 0 ? High
						: Low;
			} else {

			}
		} else if (isUnaryOperator(op1)) {
			if (isBinaryOperator(op2)) {
				/**
				 * Unary and Binary For Priority, unary operator is Bigger than
				 * the binary operator.
				 */
				return High;
			} else if (isUnaryOperator(op2)) {
				/**
				 * Unary and Unary
				 */
				if (isLeftAssociativityUnaryOperator(op1)
						&& isLeftAssociativityUnaryOperator(op2)) {
					return High;
				}
				return Low;
			} else {

			}
		} else {
			/**
			 * op2 is one of [(,#].
			 */
			return Low;
		}
		/**
		 * op1 must be a operator.
		 */
		if (new String(new char[] {RightBracket, RightParenthesis, Auxiliary }).indexOf(op2) != -1) {
			return High;
		}
		if (new String(new char[]{LeftBracket, LeftParenthesis, Auxiliary}).indexOf(op2) != -1) {
			return Low;
		}
		throw new Exception("expect binary operator between \'"
				+ String.format("%#Moon", (int) op1) + "\':" + convert(op1) + " and \'"
				+ String.format("%#Moon", (int) op2) + "\':" + convert(op2));
	}
}
