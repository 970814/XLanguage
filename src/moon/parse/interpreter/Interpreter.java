package moon.parse.interpreter;

import moon.compile.util.Exec;
import moon.compile.util.variable.Vars;
import moon.compile.Compiler;
import moon.compile.code.Code;

/**
 * Created by Administrator on 2016/5/22.
 */
public class Interpreter extends Compiler {
    Exec exec;
    public Interpreter(Vars vars) {
        super(vars);
        exec = new Exec(vars);
    }


    public double interpret(Code binCode) throws Exception {
        return binCode.run(exec);
    }

}
