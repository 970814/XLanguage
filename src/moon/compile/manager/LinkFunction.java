package moon.compile.manager;

import moon.compile.util.analyst.priority.Operator;
import moon.compile.util.variable.Vars;
import moon.compile.function.Function;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Administrator on 2016/5/26.
 */
public class LinkFunction extends Operator {
    CompileStack compileStack;
    Queue<StringBuilder> codes;
    Vars vars;

    public LinkFunction(Vars vars) {
        this.vars = vars;
        codes = null;
        compileStack = null;
    }

    public void analysis(String s) throws Exception {
//        System.out.println("start");
//        JOptionPane.showMessageDialog(null, "start:"+s);
        int i = -1;
        codes = null;
        compileStack = null;

        int size = s.length();
        char[] chs = new char[size];
        toCharArray(s, chs);
        StringBuilder code = new StringBuilder();
        boolean isLast = false;
        int lines = 0;
        int index = -1;
        try {
            do {

                while ((compileStack == null || compileStack.getEs() != 0) && ++i < size) {
//                    JOptionPane.showMessageDialog(null, "com:" + compileStack);
//                    JOptionPane.showMessageDialog(null, "array:" + Arrays.toString(Arrays.copyOfRange(chs, i, size)));

                    char ch = chs[i];
                    if (!isLast && (ch == ' ' || ch == '\t')) {
                        continue;
                    }
                    if (ch == '\n' || ch == '\r') {

                        if (compileStack != null) {
                            compileStack.add(ch);
                        }
                        lines++;
                        if (isLast) {
                            code.append(' ');
                        }
                        continue;
                    }
                    if (ch == ';' || ch == '{' || ch == '}') {
                        isLast = false;
                        if (code.length() > 0) {
                            if (compileStack == null) {
                                String f = code.toString().trim();
                                if (f.endsWith(":")) {
                                    f = f.substring(0, f.length() - 1).trim();
                                    index = vars.getIndex(f);
                                    if (index == -1) {
                                        break;
//                                        throw new Exception("function called \'" + f + "\' has been not defined.");

                                    } else {
                                        if (!(vars.get(index) instanceof Function)) {
                                            throw new Exception("\'" + f + "\' is not a function.");
                                        } else if (((Function) vars.get(index)).isNative()) {
                                            throw new Exception("native function has already been defined.");
                                        } else {
                                            compileStack = new CompileStack();
                                            compileStack.add(ch);
                                            codes = new LinkedList<>();
                                        }
                                    }
                                }
                                //ignore the code.
                                code = new StringBuilder();
                                continue;
                            }
                        } else if (compileStack == null) {
                            //code size == 0 and find no function.
                            continue;
                        }
                        boolean check = true;
                        if (code.toString().trim().length() != 0) {
                            check = false;
                            codes.add(code);
                            compileStack.add(CODE, false);
                        }
//                        JOptionPane.showMessageDialog(null, code);
                        if (ch != ';') {

                            if (check && ch == '}' && codes.size() > 0) {
                                String previous = ((LinkedList<StringBuilder>) codes).getLast().toString();
//                                JOptionPane.showMessageDialog(null, previous);
                                if (previous.startsWith("if") || previous.startsWith("else") || previous.startsWith("while")) {
                                    check = false;
                                }
                            }
                            compileStack.add(ch, check);
                        }
                        code = new StringBuilder();
                        continue;
                    }
                    code.append(ch);
                    isLast = true;
                }
                if (compileStack != null) {
//                    JOptionPane.showMessageDialog(null,"link:"+compileStack);
                    compileStack.check();
                    if (compileStack.size() == 0) {
                        compileStack.toExp();
                    }
//                    System.out.println(compileStack);
//                    System.out.println(codes);
                    ((Function) vars.get(index)).functionLinked(compileStack, codes);
                    codes = null;
                    compileStack = null;
                } /*else {
                    break;
                }*/
            } while (i < size);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("源代码结构错误:解析到第" + (++lines) + "行, 第" + (i + 1)
                    + "个字符: \'" + String.format("%#x", (int) chs[i - 1]) + "\': " + e.getMessage());
        }
//        System.out.println("end");
    }
}
