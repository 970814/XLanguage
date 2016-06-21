package test;

import moon.compile.util.analyst.priority.OperatorPriority;

/**
 * Created by Administrator on 2016/5/13.
 */
public class Test8 extends OperatorPriority{
    private Test8() {
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < AllOperators.length; i++) {
            for (int j = 0; j < AllOperators.length; j++) {
                String r = outResult(AllOperators[i], AllOperators[j]);
                System.out.println(r);
            }
        }

    }

    private static String outResult(char op1, char op2) throws Exception {

        String s = "Undefined";
        try {
            int r = OperatorPriority.priorityCompare(op1, op2);
            if (r == High) {
                s = "high";
            }
            if (r == Low) {
                s = "low";
            }
            if (r == Equal) {
                s = "euqal";
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return s;
    }
}
