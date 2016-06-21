package test;

/**
 * Created by Administrator on 2016/5/30.
 */
public class Test22 implements Runnable {

    public static void main(String[] args) {

        int n = 0;
        int s = 0;
        for (;; n++) {
            s += n * n;
            if (s > 10051005) {
                break;
            }
        }
        System.out.println(n);
    }

    public static int smallestNumber(int v) {
        int n = -1;

        int s = 0;
        while (s < v) {
            ++n;
            s += n * n;
        }
        return n;
    }
    public static int smallestNumber0(int v) {
        int n = 0;
        int s = 0;
        for (;; n++) {
            s += n * n;
            if (s > 10051005) {
                break;
            }
        }
        return n;
    }




    public void shutdown() {
        shutdown = true;
    }

    private boolean shutdown = false;
    @Override
    public void run() {
        int i = 0;
        while (!shutdown) {
            System.out.print("alive" + (++i % 20 == 0 ? "\n" : ""));
        }
    }
}
/*      Test22 test22 = new Test22();
        new Thread(test22).start();
        test22.shutdown();


        System.out.println(smallestNumber(10051005));
        System.out.println(smallestNumber0(10051005));
        */