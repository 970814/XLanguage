package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import moon.compile.util.analyst.priority.Operator;

/**
 * Created by Administrator on 2016/5/12.
 */
public class Test6 extends Operator {
	public static void main(String[] args) throws FileNotFoundException {
		System.setOut(new PrintStream(new File("m.txt")));
		System.out.println(AllOperators.length);
		for (int i = 1, size = AllOperators.length; i < size; i++) {
//			if ((int) AllOperators[i] != i) {
//				System.out.println("error defined");
//				break;
//			}
			System.out.println(convert(AllOperators[i]) + ", "
					+ AllOperators[i] + ", " + (int) AllOperators[i]);
		}

		System.out.printf("%#Moon\n", (int) ' ');
		System.out.println((char) 0x2e);
	}
}
