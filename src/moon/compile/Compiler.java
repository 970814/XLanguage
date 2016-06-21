package moon.compile;

import moon.compile.util.analyst.ExpressionAnalyst;
import moon.compile.util.analyst.ResolvedStack;
import moon.compile.code.Code;
import moon.compile.util.variable.Values;
import moon.compile.util.variable.Vars;

/**
 * Created by Administrator on 2016/5/18.
 */
public class Compiler {
    protected Vars vars;// args
    private Values values;// constant

    protected ResolvedStack resolvedStack;
    protected ExpressionAnalyst analyst;

    public Compiler(Vars vars) {
        this.vars = vars;
        values = new Values(vars);

        resolvedStack = new ResolvedStack();
        analyst = new ExpressionAnalyst(vars, values,
                resolvedStack);
    }

    public Code compile(String src) throws Exception {
        analyst.analysis(src);
        return new Code(resolvedStack.toString(), (Values) values.clone());
    }
    public Code compile0(String src) throws Exception {
        analyst.analysis(src);
        return new Code(resolvedStack.toString(), (Values) values.clone(), src);
    }

    public Vars getVars() {
        return vars;
    }
}
