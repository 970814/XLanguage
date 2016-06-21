package moon.compile.sourcecode;

import moon.compile.Compiler;
import moon.compile.code.Code;
import moon.mechanism.ReturnMechanism;
import moon.parse.interpreter.Interpreter;

/**
 * Created by Administrator on 2016/5/21.
 */
public class SourceCode {
	String sourceCode;
	CodeSet sourceCodes;
	Code binCode;
	Integer line;

	public void setLine(int line) {
		if (this.line != null) {
			return;
		}
		this.line = line + 1;
	}

	public SourceCode(String code) {
		this(null, code);
	}

	public SourceCode(CodeSet sourceCodes) {
		this(sourceCodes, null);
	}

	private SourceCode(CodeSet sourceCodes, String code) {
		this.sourceCodes = sourceCodes;
		sourceCode = code;
	}

	public CodeSet getSourceCodes() {
		return sourceCodes;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	@Override
	public String toString() {
		return "[" + sourceCode + ", " + binCode + ", next-> " + sourceCodes + "]";
	}

	public double interpretedBy(Interpreter interpreter) throws Exception, ReturnMechanism {
		try {
			if (sourceCode == null) {
				return sourceCodes.interpretAll(interpreter);
			}
			if (binCode == null) {
				binCode = interpreter.compile(sourceCode);
			} else {
				binCode.updateValues(interpreter.getVars());
			}
			return interpreter.interpret(binCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("解释到第" + line + "行(逻辑行): " + e.getMessage());
		}
	}

	public void compileBy(Compiler compiler) throws Exception {
		try {
			if (sourceCode == null) {
				sourceCodes.compileAll(compiler);
			}
			if (binCode == null) {
				binCode = compiler.compile(sourceCode);
			}
		} catch (Exception e) {
			throw new Exception("解释到第" + (line == null ? "最后" : line) + "行(逻辑行): " + e.getMessage());
		}
	}

}
