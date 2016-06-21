package moon.compile.code;

import moon.compile.util.Exec;
import moon.compile.DeCompiler;
import moon.compile.util.variable.Data;
import moon.compile.util.variable.Values;
import moon.compile.util.variable.Vars;

/**
 * Created by Administrator on 2016/5/18.
 */
public class Code {
    private String rawCode = null;

    public Code(String s, Values clone, String src) {
        this.binCode = s;
        this.values = clone;
        this.rawCode = src;
    }

    public String getRawCode() {
        return rawCode;
    }

    protected String binCode;//已编译的二进制代码

    private Values values;// 固定常量

    public Code(String binCode, Values values) throws Exception {
        this(binCode, values, null);
    }

    public double run(Exec exec) throws Exception {

        return exec.exec(binCode, (Values) values.clone());
    }

    public void updateValues(Vars vars) {
        Values tmp = new Values();
        for (Data value : values) {
            if (value.getName() == null || value.isNewVar()) {
                tmp.add(value);
                continue;
            }
            int i = 0;
            int size = vars.size();
            for (; i < size; i++) {
                Data var = vars.get(i);
                if (value.getName().equals(var.getName()) && value.getClass().equals(var.getClass())) {
                    tmp.add(var);
                    if (var instanceof Vars.Array) {
                        var.clear();
                    }
                    break;
                }
            }
            if (i >= size) {
                tmp.add(value);
            }
        }
        values = tmp;
    }


    @Override
    public String toString() {
        if (rawCode != null) {
            return rawCode;
        }
        return deCompile();
    }

    private String deCompile() {
        return DeCompiler.deCompile(binCode);
    }
}
