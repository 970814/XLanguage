package moon.compile.sourcecode;

import moon.mechanism.ReturnMechanism;
import moon.parse.interpreter.Interpreter;

/**
 * Created by Administrator on 2016/5/23.
 */
public class IfSourceCode extends SourceCode {
    boolean hasElse = false;

    public void hasElse() {
        hasElse = true;
    }

    public boolean isHasElse() {
        return hasElse;
    }

    public IfSourceCode(String code) {
        super(code);
    }

    CodeSet value_if_true;
    CodeSet value_if_false;

    public void setValue_if_false(CodeSet value_if_false) {
        this.value_if_false = value_if_false;
    }

    public void setValue_if_true(CodeSet value_if_true) {
        this.value_if_true = value_if_true;
    }

    public CodeSet getValue_if_false() {
        return value_if_false;
    }

    public CodeSet getValue_if_true() {
        return value_if_true;
    }

    @Override
    public String toString() {
        return super.toString() + "if[" + value_if_true +", "+ value_if_false+" ]";
    }

    @Override
    public double interpretedBy(Interpreter interpreter) throws Exception, ReturnMechanism {
        double v = super.interpretedBy(interpreter);
        if (v != 0.0) {
            return value_if_true.interpretAll(interpreter);
        } else if (hasElse) {
            return value_if_false.interpretAll(interpreter);
        }
        return v;
    }
}
