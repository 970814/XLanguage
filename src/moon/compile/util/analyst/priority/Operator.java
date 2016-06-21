package moon.compile.util.analyst.priority;

import moon.compile.util.analyst.constant.BaseDataConstant;
import moon.compile.util.analyst.constant.KeyWord;

/**
 * Created by Administrator on 2016/5/10.
 */
public abstract class Operator implements BaseDataConstant {
    static String allOperators = new String(AllOperators);
//    static String logicalOperators = new String(LogicalOperators);
    static String binaryOperators = new String(BinaryOperators);
    static String unaryOperators = new String(UnaryOperators);
    static String leftAssociativityUnaryOperators = new String(LeftAssociativityUnaryOperators);
    static String rightAssociativityUnaryOperators = new String(RightAssociativityUnaryOperators);
    static String varOperator = new String(VarOperator);
    protected static boolean isOperator(char op) {
        return allOperators.indexOf(op) != -1;
    }

//    protected static boolean isLogicalOperator(char op) {
//        return logicalOperators.indexOf(op) != -1;
//    }

    protected static boolean isBinaryOperator(char op) {
        return binaryOperators.indexOf(op) != -1;
    }

    protected static boolean isUnaryOperator(char op) {
        return unaryOperators.indexOf(op) != -1;
    }

    protected static boolean isLeftAssociativityUnaryOperator(char op) {
        return leftAssociativityUnaryOperators.indexOf(op) != -1;
    }

	protected static boolean isRightAssociativityUnaryOperator(char op) {
        return rightAssociativityUnaryOperators.indexOf(op) != -1;
    }

    protected static boolean isVarOperator(char op) {
        return varOperator.indexOf(op) != -1;
    }
    public static String convert(char binOP) {
        if (binOP == Data) {
            return "$";
        }
        if (binOP == VAR) {
            return "@";
        }
        if (binOP == Auxiliary) {
            return "#";
        }
        if (binOP == InvokeFunction) {
            return "InvokeFunction";
        }

        String[] strs = KeyWord.STRINGS.get(allOperators.indexOf(binOP));
        return strs[0];
    }
    public static void toCharArray(String expStr, char[] exp) {
        for (int i = 0, size = expStr.length(); i < size; i++) {
            exp[i] = expStr.charAt(i);
        }
    }
}
