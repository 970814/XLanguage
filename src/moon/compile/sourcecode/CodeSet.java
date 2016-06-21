package moon.compile.sourcecode;

import moon.compile.Compiler;
import moon.mechanism.ReturnMechanism;
import moon.parse.interpreter.Interpreter;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/5/21.
 */
public class CodeSet extends LinkedList<SourceCode> {
	private CodeSet previous;

	public CodeSet getPrevious() {
		return previous;
	}

	public CodeSet(CodeSet previous) {
		this.previous = previous;
	}

	@Override
	public void clear() {
		previous = null;
		super.clear();
	}

	public CodeSet compileAll(Compiler compiler) throws Exception {
		for (SourceCode sourceCode : this) {
			if (sourceCode.sourceCode != null) {
				sourceCode.compileBy(compiler);
			} else {
				sourceCode.sourceCodes.compileAll(compiler);
			}
		}
		return this;
	}

	double returnValue;

	public double interpretAll(Compiler compiler) throws Exception, ReturnMechanism {
		returnValue = -1.0;
		if (!(compiler instanceof Interpreter)) {
			compileAll(compiler);
			return -2.0;
		}

		Interpreter interpreter = (Interpreter) compiler;
		/**
		 * for (SourceCode sourceCode : this) {
		 * returnValue = sourceCode.interpretedBy(interpreter);
		 * }
		 */
		int i = 0;
		try {
			for (; i < size(); i++) {
				SourceCode code = get(i);
				returnValue = code.interpretedBy(interpreter);
				if (code instanceof ReturnSourceCode) {
					throw new ReturnMechanism(returnValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Interpretation Exception: interpret the " + (++i) + " code: " + e.getMessage());
		}
		return returnValue;
	}
}
