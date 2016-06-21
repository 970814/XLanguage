package test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by Administrator on 2016/6/8.
 */
public class Test26 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the each value of operand:");

        for (int i = 0; i < integers.length; i++) {
            integers[i] = scanner.nextInt();
            System.out.println(integers[i]);
        }
        run(new Stack<>());

    }
    final static int[] integers = new int[6];
    final static char[] operators = new char[]{'+', '-', '*', '/'};

    public static void run(Stack<Character> stack) {
        if (stack.size() >= 4) {
            equal(stack);
            return;
        }
        for (int i = 0; i < operators.length; i++) {
            stack.push(operators[i]);
            run(stack);
            stack.pop();
        }
    }

    public static boolean equal(Stack<Character> operators) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < operators.size(); i++) {
            builder.append(integers[i]);
            builder.append(operators.get(i));
        }
        builder.append(integers[4]);
        System.out.println(builder);
        return true;
    }
    public static int cal() {
        return 0;
    }
}
