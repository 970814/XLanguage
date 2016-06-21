package test0;

/**
 * Created by Administrator on 2016/6/7.
 */
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
public class MainFrame extends JFrame {

    /**
     *
     */
    JSplitPane jSplitPane1 = new JSplitPane();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new MainFrame();
    }

    public void myinit() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设定窗体关闭后自动退出进程
        this.setSize(800, 600);//设定窗体的默认尺寸
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);//设定窗体状态为屏幕最大化，即全屏尺寸。
        this.setVisible(true);//显示窗体
        this.jSplitPane1.setDividerLocation(0.7);//设定分割面板的左右比例(这时候就生效了，如果放在setVisible(true)这据之前就不会有效果。)
        this.addComponentListener(new ComponentAdapter() {

            public void componentResized(ComponentEvent e) {

                jSplitPane1.setDividerLocation(0.7);
            }
        });
    }

    public MainFrame() {
        try {

            jbInit();
            myinit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jSplitPane1.add(jPanel1, JSplitPane.LEFT);
        jSplitPane1.add(jPanel2, JSplitPane.RIGHT);
        jSplitPane1.setEnabled(false);
        jSplitPane1.setOneTouchExpandable(true);

    }


}
