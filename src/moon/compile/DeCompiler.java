package moon.compile;

import moon.compile.util.analyst.priority.Operator;

import java.util.Stack;

/**
 * Created by Administrator on 2016/5/18.
 */
public class DeCompiler extends Operator {
    public static String deCompile(String binCode) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < binCode.length(); i++) {
            builder.append(convert(binCode.charAt(i)));
        }
        return builder.toString();
    }

    public static String deCompile(Stack characterResolvedStack) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = characterResolvedStack.size(); i < size; i++) {
            builder.append(characterResolvedStack.get(i));
        }
        return deCompile(builder.toString());
    }
}
