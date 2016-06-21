package moon.compile.util;

/**
 * Created by Administrator on 2016/5/4.
 */
public class Gamma {


    /**
     * You can only invoke this method.
     *
     * @param x input variable
     * @return The factorial of variable x.
     */
    public static double decimal_factorial(double x) {
        if ((double) (long) x == x) {
            return (double) factorial((long) x);
        }
        return gamma(x + 1);
    }

    private static double constants[] = { 0.0000677106, -0.0003442342,
            0.0015397681, -0.0024467480, 0.0109736958, -0.0002109075,
            0.0742379071, 0.0815782188, 0.4118402518, 0.4227843370, 1.0 };

    private static long factorial(long x) {
        if (x < 0) {
            throw new IllegalArgumentException(
                    "Factorial of a negative number can not be calculated");
        }
        if (x == 0.0) {
            return 1;
        }
        long r = 1L;
        for (; x > 0; x--) {
            r *= x;
        }
        return r;
    }

    private static double gamma(double x) {
        int i;
        double y, t, s, u;

        if (x <= 0.0) {
            throw new IllegalArgumentException(
                    "Factorial of a negative number can not be calculated");
        }
        y = x;
        if (y <= 1.0) {
            t = 1.0 / (y * (y + 1.0));
            y = y + 2.0;
        } else if (y <= 2.0) {
            t = 1.0 / y;
            y = y + 1.0;
        } else if (y <= 3.0) {
            t = 1.0;
        } else {
            t = 1.0;
            while (y > 3.0) {
                y = y - 1.0;
                t = t * y;
            }
        }
        s = constants[0];
        u = y - 2.0;
        for (i = 1; i <= 10; i++) {
            s = s * u + constants[i];
        }
        s = s * t;
        return (s);
    }
}
