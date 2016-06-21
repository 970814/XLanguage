package test;

import java.awt.EventQueue;
import java.awt.ScrollPane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Administrator on 2016/5/13.
 */
public class Test10 {
	public static void main(String[] args) {
		new PriorityPanel() {
			{
				pack();
				setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				setLocationByPlatform(true);
			}
		}.setVisible(true);
	}
}
