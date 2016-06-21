package moon.compile.sourcecode;

import moon.mechanism.ReturnMechanism;
import moon.parse.interpreter.Interpreter;

/**
 * Created by Administrator on 2016/5/23.
 */
public class WhileSourceCode extends SourceCode {
    public WhileSourceCode(String code) {
        super(code);
    }

    CodeSet loop;

    public void setLoop(CodeSet loop) {
        this.loop = loop;
    }

    public CodeSet getLoop() {
        return loop;
    }

    @Override
    public String toString() {
        return super.toString() + "while[" + loop + " ]";
    }

    @Override
    public double interpretedBy(Interpreter interpreter) throws Exception, ReturnMechanism {
        double v;
        while ((v = super.interpretedBy(interpreter)) != 0.0) {
            loop.interpretAll(interpreter);
        }
        return v;
    }

    @Deprecated
    public double interpretedBy0(Interpreter interpreter) throws Exception, ReturnMechanism {
        double v = super.interpretedBy(interpreter);
        if (v != 0.0) {
            loop.interpretAll(interpreter);
            return interpretedBy(interpreter);
        }
        return v;
    }
}
