package test;

import java.util.Scanner;

import moon.compile.util.variable.Values;
import moon.compile.util.variable.Vars;
import moon.compile.util.analyst.ExpressionAnalyst;
import moon.compile.util.analyst.ResolvedStack;
import moon.compile.util.Exec;

public class T {
	protected Vars vars = new Vars();// args
	private Values values = new Values(vars);// constant

	protected Exec exec = new Exec(vars);
	protected ResolvedStack resolvedStack = new ResolvedStack();
	protected ExpressionAnalyst analyst = new ExpressionAnalyst(vars, values,
			resolvedStack);

	public void run() throws Exception {
		System.out.println();
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				String s = scanner.nextLine();
				try {
					System.out.println(cal(s));
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(vars);
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public static String compile() {

		return null;
	}


	public double cal(String s) throws Exception {
		analyst.analysis(s);
		return exec.exec(resolvedStack.toString(), values);
	}

	public static void main(String[] args) throws Exception {
		new T().run();
	}
}
