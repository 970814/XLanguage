package test;

import java.util.Scanner;

import moon.compile.util.Exec;
import moon.compile.util.variable.Data;
import moon.compile.util.variable.Values;
import moon.compile.util.variable.Vars;
import moon.compile.util.analyst.ExpressionAnalyst;
import moon.compile.util.analyst.ResolvedStack;

public class Test13 {
	public static void main(String[] args) throws Exception {
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
		try (Scanner scanner = new Scanner(System.in)) {

			do {
				String s = scanner.nextLine();
				try {
					analyst.analysis(s);
					analyst.showResult();
					System.out.println(resolvedStack);
					System.out.println(exec.exec(resolvedStack.toString(),
							values));

				} catch (Exception e) {
					e.printStackTrace();
				}

			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double f(String s, Data... datas) throws Exception {
		if (s.equals("print")) {
			return print(datas);
		}

		Values values = new Values();
		for (int i = 0; i < datas.length; i++) {
			values.add(datas[i]);
		}
		return new Exec(values.getVars()).exec(s, values);
	}

	public static double print(Data... datas) throws Exception {
		int i = 0;
		for (; i < datas.length; i++) {
			if (datas[i] == null)
				break;
			System.out.print(datas[i].getValue() + ", ");
		}
		System.out.println();
		return i;
	}
}
