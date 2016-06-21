package moon.compile;

import moon.mechanism.ReturnMechanism;
import moon.compile.util.variable.Vars;
import moon.compile.sourcecode.CodeSet;
import moon.parse.interpreter.Interpreter;

import java.util.Scanner;

public class SourceCodeCompiler {
	StringAnalyst stringAnalyst;
	SourceCodeAnalyst sourceCodeAnalyst;

	public SourceCodeCompiler() {
		stringAnalyst = new StringAnalyst();
		sourceCodeAnalyst = new SourceCodeAnalyst();
	}

	public CodeSet analysis(String src) throws Exception {
		stringAnalyst.analysis(src);
		return sourceCodeAnalyst.compile(stringAnalyst.compileStack,
				stringAnalyst.codes);
	}

	public static void main(String[] args) {
		System.out.println();
		SourceCodeCompiler sourceCodeCompiler = new SourceCodeCompiler();
		Vars vars = new Vars();
		Interpreter interpreter = new Interpreter(vars);
		sourceCodeCompiler.defineFunction(vars);
//		sourceCodeCompiler.nonDefineFunction();
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				String s = scanner.nextLine();
				try {
					CodeSet codeSet = sourceCodeCompiler.analysis(s);
					for (int i = 0; i < 5; i++) {
						System.out.println("return: " + codeSet.interpretAll(interpreter));
					}
				} catch (Exception e) {
					e.printStackTrace();
				} catch (ReturnMechanism returnMechanism) {
					returnMechanism.printStackTrace();
				}
				System.out.println(vars);
			} while (true);
		}
	}

	public void nonDefineFunction() {
		sourceCodeAnalyst.nonDefineFunction();
		stringAnalyst.defining(false);
	}

	public void defineFunction(Vars vars) {
		sourceCodeAnalyst.defineFunction(vars);
		stringAnalyst.defining(true);
	}

}
