package test;

import moon.compile.util.analyst.constant.KeyWord;

/**
 * Created by Administrator on 2016/5/12.
 */
public class Test7   {
    public static void main(String[] args) {
        System.out.println(KeyWord.STRINGS.size());
        int[] ar = new int[2];
        ar[ar[ar[ar[0]]]] = 1;

    }
}
