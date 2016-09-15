import java.util.ArrayList;

/**
 * Created by gmarson on 9/14/2016.
 */
public abstract class Function {
    private int maxValue, minValue;

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public abstract Object f(ArrayList<Double> x);
}
