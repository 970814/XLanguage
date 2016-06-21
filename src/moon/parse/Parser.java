package moon.parse;

import moon.Moon;
import moon.compile.function.Function;
import moon.compile.manager.OperationManager;

import javax.swing.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Administrator on 2016/6/3.
 */
public class Parser {
    OperationManager operationManager = null;
    public LinkedList<File> allFiles = new LinkedList<>();

    /**
     * if file is has existed, do nothing and return false.
     * else add it, then return true.
     */
    private boolean add(File file) {
        if (!allFiles.contains(file)) {
            return allFiles.add(file);
        }
        return false;
    }

    public Parser(String... fileNames) throws Exception {
        for (int i = 0; i < fileNames.length; i++) {
            linkNewFile(fileNames[i]);
        }
    }

    public Parser(File... files) throws Exception {
        for (File file : files) {
            linkNewFile(file);
        }
    }


    public Vector<Function> compileAll() throws Exception {
        operationManager = new OperationManager();
        compile(allFiles);
        operationManager.adminAllFunction();
        return operationManager.getFunctionSet();
    }

    String s = "setStdoutToFile:";
    private void importNewFile(File file) throws Exception {
        StringBuilder builder = OperationManager.readSourceCode(file);
        String[] lines = builder.toString().split("\n");

        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
            if (lines[i].startsWith("import ") && lines[i].endsWith(";")) {
                lines[i] = lines[i].substring(7, lines[i].length() - 1);
                linkNewFile(new File(file.getParent(), lines[i]));
            } else if (lines[i].startsWith(s) && lines[i].endsWith(";")) {
                lines[i] = lines[i].substring(s.length(), lines[i].length() - 1);
                Moon.setOut(lines[i].trim());
            }
        }
    }

    public void linkNewFile(String fileName) throws Exception {
        linkNewFile(new File(Moon.getParent(), fileName));
    }
    private void linkNewFile(File file) throws Exception {
        if (add(file)) {
            //if success to add the file.
            importNewFile(file);
        } else {
            JOptionPane.showMessageDialog(null, "file: " + file + " has already existed.");
        }
    }

    public void compile(File file) throws Exception {
        try {
            operationManager.defineFunctionPrototypes(file);
            operationManager.linkFunction(file);
        } catch (Exception e) {
            throw new Exception("parsing the file: \'" + file + "\' " + e.getMessage());
        }

    }



    private void compile(LinkedList<File> files) throws Exception {
        for (int i = 0; i < files.size(); i++) {
            compile(files.get(i));
        }
    }

}
