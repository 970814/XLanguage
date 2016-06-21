package test;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/5/11.
 */
public class Test4 {
    final private LinkedList<String> strings;

    public Test4(LinkedList<String> strings) {
        this.strings = strings;
    }

    public boolean del(String s) {
        return strings.remove(s);
    }
}
