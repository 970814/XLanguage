package test;

import moon.compile.util.analyst.constant.BaseDataConstant;

/**
 * Created by Administrator on 2016/5/13.
 */
public class Test9 implements BaseDataConstant {
    public static void main(String[] args) {
        System.out.println(AllOperators.length);
        for (int i = 0; i < AllOperators.length; i++) {
            System.out.printf("%#Moon\n", (int) AllOperators[i]);
        }
    }
}
