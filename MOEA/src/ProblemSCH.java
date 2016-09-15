import java.util.ArrayList;

/**
 * Created by gmarson on 9/15/2016.
 */

public class ProblemSCH extends Function {

    public ProblemSCH()
    {
        this.setMaxValue(Integer.MAX_VALUE);
        this.setMinValue(Integer.MIN_VALUE);
    }

    public Object f(ArrayList<Double> x)
    {

        return null;
    }

    public int firstFunction(double x)
    {
        return (int) Math.pow(x,2);
    }

    public int secondFunction(double x)
    {
        return (int) Math.pow(x-2,2);
    }


}
