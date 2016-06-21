package moon.compile.function;

import moon.compile.util.variable.Vars;

import java.util.Objects;

/**
 * Created by Administrator on 2016/5/30.
 */
public class FPointer extends moon.compile.util.variable.Data {

    public FPointer(String name) throws Exception {
        super(name);
    }

    private Function function;
    private String pointer;

    @Override
    public String toString() {
        return "fp: " + name + "ref: " + Objects.toString(pointer);
    }

    public void reference(String f) {
        pointer = f;
    }

    public Function getFunction(Vars vars) throws Exception {
        Function function = vars.getFunctionCalled(pointer);
        if (function == null) {
            throw new Exception("function pointer did not call any function.");
        }
        return function;
    }

    @Override
    public void setValue(double value) throws Exception {
        throw new Exception("function pointer is incompatible for constants.");
    }

    public String getPointer() {
        return pointer;
    }
}
