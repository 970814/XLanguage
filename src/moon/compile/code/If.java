package moon.compile.code;

import moon.compile.util.Exec;
import moon.compile.util.variable.Values;

/**
 * Created by Administrator on 2016/5/18.
 */
public class If extends Code {
    Code value_if_true;
    Code value_if_false;

    public If(String binCode, Values values) throws Exception {
        super(binCode, values);
        value_if_true = null;
        value_if_false = null;
    }

    @Override
    public double run(Exec exec) throws Exception {
        double result = super.run(exec);
        if (result != 0.0) {
            //if value is true.
            if (value_if_true != null) {
                return value_if_true.run(exec);
            }
        } else {
            //if value is false.
            if (value_if_false != null) {
                return value_if_false.run(exec);
            }
        }
        return result;
    }

    public void setValue_if_true(Code value_if_true) {
        this.value_if_true = value_if_true;
    }

    public void setValue_if_false(Code value_if_false) {
        this.value_if_false = value_if_false;
    }
}
