import java.util.ArrayList;

/**
 * Created by gmarson on 9/15/2016.
 * TCC UFU
 */

public class ProblemSCH extends Function {
    private int maxValue, minValue;
    private int numberOfFunctions;

    private static ProblemSCH function = new ProblemSCH();


    public static ProblemSCH getInstance() {
        return function;
    }

    private ProblemSCH()
    {
        this.setNumberOfFunctions(2);
        this.setMaxValue(1000);
        this.setMinValue(-1000);
    }

    public static void setFunction(ProblemSCH function) {
        ProblemSCH.function = function;
    }

    public int firstFunction(double x)
    {
        return (int) Math.pow(x,2);
    }
    public int secondFunction(double x)
    {
        return (int) Math.pow(x-2,2);
    }

    public int getNumberOfFunctions() {
        return numberOfFunctions;
    }


    public double getMaxValue() {
        return  maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setNumberOfFunctions(int numberOfFunctions) {
        this.numberOfFunctions = numberOfFunctions;
    }

    public static Function getFunction() {
        return function;
    }



}
