package moon.compile.manager;

import moon.compile.util.variable.Data;
import moon.compile.util.variable.Vars;
import moon.compile.SourceCodeCompiler;
import moon.compile.function.Function;
import moon.compile.sourcecode.CodeSet;


import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Administrator on 2016/5/25.
 */
public class OperationManager {

    SourceCodeCompiler sourceCodeCompiler = new SourceCodeCompiler();
    Vars vars = new Vars();
//    Interpreter interpreter = new Interpreter(vars);
    LinkFunction linkFunction = new LinkFunction(vars);
    Vector<Function> functionSet = null;
    public OperationManager() {
    }

    public Vector<Function> getFunctionSet() {
        return functionSet;
    }

    public static StringBuilder readSourceCode(File src) throws Exception {
        StringBuilder builder = new StringBuilder();
        try (Scanner scanner = new Scanner(src)) {
            String code;
            boolean expected = false;
            try {
                while (true) {
                    code = scanner.nextLine().replace("\t"," ");
                    int index = code.indexOf("//");
                    if (index != -1) {
                        code = code.substring(0, index);
                    }
                    String unused = null;
                    if(!expected) {
                        index = code.indexOf("/*");
                        if (index != -1) {
                            expected = true;
                            unused = code.substring(index + 2, code.length());
                            code = code.substring(0, index);
                            builder.append(code);
                        }
                    }
                    if (expected) {
                        if (unused != null) {
                            index = unused.indexOf("*/");
                            if (index != -1) {
                                expected = false;
                                code = unused.substring(index + 2, unused.length());
                            }
                        } else {
                            index = code.indexOf("*/");
                            if (index != -1) {
                                expected = false;
                                code = code.substring(index + 2, code.length());
                            }
                        }
                    }
//                    JOptionPane.showMessageDialog(null, code);
                    if (!expected) {
                        builder.append(code + '\n');
                    }
                    builder.append("\n");
                }
            } catch (java.util.NoSuchElementException e) {
//                e.printStackTrace();
                if (expected) {
                    throw new Exception("Note the use of Error, expect \'*/\'");
                }

            }
        } catch (FileNotFoundException e) {
            File file = new File("mre", src.getName());
            if (!file.exists()) {
                throw new FileNotFoundException("cannot find the file: \'" + file.getCanonicalPath() + "\'");
            } else {
                return readSourceCode(file);
            }
//            if (src.getParentFile().getCanonicalPath().equals(Moon.getParent().getCanonicalPath())) {
//                return readSourceCode();
//            }
//            throw new FileNotFoundException("cannot find the file: \'" + src.getCanonicalPath() + "\'");
        }
//        JOptionPane.showMessageDialog(null, builder.toString());
        return builder;
    }

    public void defineFunctionPrototypes(File src) throws Exception {
        sourceCodeCompiler.defineFunction(vars);
        StringBuilder builder = readSourceCode(src);

//        System.out.println(builder.toString());
        analysis(builder.toString());
    }

    public void linkFunction(File src) throws Exception {
        sourceCodeCompiler.nonDefineFunction();
        StringBuilder builder = readSourceCode(src);
        linkFunction.analysis(builder.toString());
//       return analysis(builder.toString());
    }

    private CodeSet analysis(String code) throws Exception {
//        System.out.println(">>>" + vars);
        CodeSet codeSet = sourceCodeCompiler.analysis(code);
//        System.out.println(">>>" + vars);
        return codeSet;
    }

//    public double run(CodeSet codeSet) throws Exception {
//        return codeSet.interpretAll(interpreter);
//    }

    public void adminAllFunction() {
        functionSet = new Vector<>();
        for (Data var : vars) {
            if (var instanceof Function) {
                functionSet.add((Function) var);
            }
        }
        for (Function function : functionSet) {
            function.setAccessRights(functionSet);
        }
    }


    public static void main(String[] args) throws Exception {
        File src = new File("workspace/prime.moon");
        OperationManager operationManager = new OperationManager();
        operationManager.defineFunctionPrototypes(src);
//        try (Scanner scanner = new Scanner(System.in)) {
//            scanner.nextLine();
//        }
        operationManager.linkFunction(src);
        operationManager.adminAllFunction();
        Vector<Function> functionSet = operationManager.getFunctionSet();
        boolean isDefined = false;
        for (int i = 0; i < 1; i++) {
            for (Function function : functionSet) {
                if (function.getName().equals("main")) {
                    isDefined = true;
                    try {
                        System.out.println(function + ": " + function.invoke());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
//            JOptionPane.showMessageDialog(null, "next");
        }
        if (!isDefined) {
            throw new Exception("在文件: \'" + src + "\' 找不到main函数原型.");
        }

    }

}
