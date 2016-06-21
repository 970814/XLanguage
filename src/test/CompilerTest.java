package test;

import moon.compile.util.Exec;
import moon.compile.code.Code;
import moon.compile.Compiler;
import moon.compile.util.variable.Data;
import moon.compile.util.variable.Vars;

import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Administrator on 2016/5/18.
 */
public class CompilerTest {
    public static void main(String[] args) throws Exception {
        Vars vars = new Vars();
        vars.newVar(new Data("a"));
        vars.newVar(new Data("b"));
        vars.newVar(new Data("c"));
        vars.newVar(new Data("d"));
        Compiler compiler = new Compiler(vars);
        Exec exec = new Exec(vars);

        try (Scanner scanner = new Scanner(System.in)) {

            Code recentCode = null;
            Vector<Code> codes = new Vector<>();
            do {
            	System.out.println(recentCode);
                menu();
                String s = scanner.nextLine().trim();
                if (s.equalsIgnoreCase("exit")) {
                    break;
                }
                if (s.equalsIgnoreCase("all code")) {
                    System.out.println(codes);
                    continue;
                }
                if (s.equalsIgnoreCase("run all code")) {
//                    codes.forEach(code -> {
//                        try {
//                            System.out.print(code.run(exec) + ", ");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        System.out.println();
//                    });
                    for (Code code : codes) {
                        try {
                            System.out.print(code.run(exec) + ", ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println();
                    }
                    continue;
                }
                if (s.length() == 0) {
                    if (recentCode == null) {
                        System.out.println("please input a code.");
                        continue;
                    } else {
                    }
                } else if (s.startsWith("run ")) {
                    s = s.replaceFirst("run ", "");
                    int i;
                    try {
                        i = new Integer(s.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    if (i < 0 || i >= codes.size()) {
                        System.out.println("the code index of " + i + " isn't exist, please rechecks");
                        continue;
                    } else {
                        recentCode = codes.get(i);
                    }
                } else {
                    Code code;
                    try {
                        code = compiler.compile(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    if (code != null) {
                        recentCode = code;
                        codes.add(recentCode);
                    } else {
                        continue;
                    }
                }
                try {
                    double v = recentCode.run(exec);
                    System.out.println(v);
                    if (v == -10.0) {
                        vars = new Vars();
                        exec = new Exec(vars);
//                        compiler = new Compiler(vars);
                    }
                } catch (Exception e) {
                    System.out.println("runtime exception: " + e.getMessage());
                }
                System.out.println(vars);
            } while (true);
        }
    }

    public static void menu() {
        System.out.println("0: input code.(7+3)");
        System.out.println("1: run the designated code.(run 6)");
        System.out.println("2: default, run the previous code.(enter)");
    }
}
