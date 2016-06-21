package test;

import java.util.Scanner;

import moon.compile.util.variable.Data;

public class Test13$0 extends Test13 {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				System.out.print("请输入可变参数:");
				String ars = scanner.nextLine();
				Data[] datas = new Data[50];
				args = ars.split(",");
				for (int i = 0; i < args.length; i++) {
					datas[i] = new Data(new Double(args[i]));
				}
				try {
					System.out.println(f(scanner.nextLine(), datas));
				} catch (Exception e) {

					e.printStackTrace();
				}
			} while (true);
		}
	}
}
