package test;

/**
 * Created by Administrator on 2016/5/24.
 */
public class Test18 {
    public static void main(String[] args) {
        double a = 0;
        double sum = 0;
        while(a<Integer.MAX_VALUE){
            sum += ++a;
        }
        System.out.println(sum);

    }
}
