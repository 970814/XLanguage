package test;

import java.util.Scanner;

import moon.compile.util.variable.Data;
import moon.compile.util.variable.Values;
import moon.compile.util.variable.Vars;
import moon.compile.util.analyst.ExpressionAnalyst;
import moon.compile.util.analyst.ResolvedStack;
import moon.compile.util.Exec;

public class Test14 {
	public static void main(String[] args) throws Exception {
//		System.setOut(new PrintStream(new File("out.txt")));
		System.out.println();
		Vars vars = new Vars();

		vars.add(new Data("a"));
		vars.add(new Data("b"));
		vars.addAr("ar", 99);
		Values values = new Values(vars);
		Exec exec = new Exec(vars);
		ResolvedStack resolvedStack = new ResolvedStack();
		ExpressionAnalyst analyst = new ExpressionAnalyst(vars, values,
				resolvedStack);
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		analyst.analysis(s);
		System.out.println(resolvedStack);
		System.out.println(exec.exec(resolvedStack.toString(),
				(Values) values.clone()));
		do {

			try {
				System.out.println(exec.exec(
						 scanner.nextLine(),
						(Values) values.clone()));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (true);
	}
}
