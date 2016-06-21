package moon.mechanism;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ReturnMechanism extends Throwable {
    final double returnValue;
    public ReturnMechanism(double returnValue) {
        this.returnValue = returnValue;
    }

    public double getReturnValue() {
        return returnValue;
    }
}
