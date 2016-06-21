package test;

/**
 * Created by Administrator on 2016/5/30.
 */
public class Test23 {
    public static void main(String[] args) {
        synchronized ("0") {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized ("1") {
                        synchronized ("0") {
                        }
                    }
                }
            }).start();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized ("1") {
            }
        }
    }
}
