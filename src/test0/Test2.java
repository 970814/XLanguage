package test0;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/6/13.
 */
public class Test2 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("readme.txt"))) {
            try {
                while (true) {
                    System.out.println(scanner.nextLine());
                }
            } catch (NoSuchElementException e1) {
                e1.printStackTrace();
                //ignore it.
            }
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage());
        }
    }
}
