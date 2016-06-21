package test;

import moon.compile.util.variable.Data;
import moon.compile.util.variable.Vars;

/**
 * Created by Administrator on 2016/5/18.
 */
public class Test16 {
    public static void main(String[] args) throws Exception {
        Vars vars = new Vars();
        vars.newVar(new Data("a"));

//        try (Scanner scanner = new Scanner(System.in)) {
//            Code code = new Code(vars, scanner.nextLine());
//            while (true) {
//                scanner.nextLine();
//                try {
//                    System.out.println(code.run());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }


    }
}
