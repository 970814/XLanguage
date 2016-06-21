package test0;

import moon.compile.util.variable.Vars;
import moon.parse.interpreter.Interpreter;

import java.util.Scanner;

/**
 * Created by Administrator on 2016/6/12.
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        Vars vars = new Vars();
        Interpreter interpreter = new Interpreter(vars);
        System.out.println("Welcome to use INTERPRETER(1.0.0.2);");
        System.out.println("Thanks for your support; CEO is Mr.L;");
        System.out.println("We are a large team;");
        System.out.println("Contact us -> mail: 14******34@qq.com;");
        System.out.println("-------------------------------------------------------");
        System.out.println("欢迎使用最新版本的解释器(1.0.0.2)");
        System.out.println("感谢支持; CEO 是刘先生;");
        System.out.println("我们是一个庞大的团队;");
        System.out.println("联系我们 -> 邮箱: 14******34@qq.com;");
        try (Scanner scanner = new Scanner(System.in);) {
            while (true) {
                System.out.print(">>> ");
                String code = scanner.nextLine().trim();
                if (code.equalsIgnoreCase("exit")) {
                    break;
                }
                try {
                    if (code.length() == 0) {
                        continue;
                    } else {
                        System.out.println(interpreter.interpret(interpreter.compile(code)));
                    }
                } catch (Exception throwable) {
//                    throwable.printStackTrace();
                    System.out.println(throwable.getMessage());
                }
            }
        }
    }
}
