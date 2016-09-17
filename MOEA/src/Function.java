import java.util.ArrayList;

/**
 * Created by gmarson on 9/14/2016.
 * TCC UFU
 */

public abstract class Function {

    protected Function(){}

    public abstract void applyFunction();

    public abstract double getMaxValue();
    public abstract void setMaxValue(int maxValue);
    public abstract int getMinValue();
    public abstract void setMinValue(int minValue);

}
