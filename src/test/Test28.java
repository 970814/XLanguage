package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by Administrator on 2016/6/11.
 */
public class Test28 {
    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        File file = new File("r.bat");
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintStream printStream = new PrintStream(file);
        printStream.println("start");
        printStream.close();
        runtime.exec("r.bat");
    }
}
