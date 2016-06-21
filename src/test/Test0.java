package test;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2016/5/9.
 */
public class Test0 {
    public static void main(String[] args) {
        new JFrame() {
            {
                setSize(800, 800);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setLocationByPlatform(true);
                JDesktopPane pane = new JDesktopPane();
                setContentPane(pane);
                pane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
                JInternalFrame frame = new JInternalFrame("frame0", true, true, true, true) {
                    {
                        setSize(100, 400);
                        setVisible(true);
                    }
                };
                JInternalFrame frame2 = new JInternalFrame("frame2", true, true, true, true){
                    {
                        setSize(100, 200);
                        setVisible(true);
                    }
                };
                pane.add(frame);
                pane.add(frame2);
            }
        }.setVisible(true);
    }
}
