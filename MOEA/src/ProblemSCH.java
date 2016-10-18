import java.util.ArrayList;

/**
 * Created by gmarson on 9/15/2016.
 * TCC UFU
 */

public class ProblemSCH extends Function {
    private int maxValue, minValue;
    private int numberOfFunctions;

    private static ProblemSCH function = new ProblemSCH();

    //Singleton
    public static ProblemSCH getInstance() {
        return function;
    }
    private ProblemSCH()
    {
        this.setNumberOfFunctions(2);
        this.setMaxValue(1000);
        this.setMinValue(-1000);
    }


    public  void applyFunction(){
        Member memberAtIndex;
        ArrayList<Object> resultingFunction = new ArrayList<>();
        ArrayList<Member> p = Population.getInstance();
        int i,j;
        for(i=0;i<Population.getInstance().size();i++)
        {
            memberAtIndex = p.get(i);

            if(memberAtIndex.getResultOfFunctions().isEmpty())
            {
                resultingFunction.add(this.firstFunction(memberAtIndex.getData()));
                resultingFunction.add(this.secondFunction(memberAtIndex.getData()));

                Population.replaceElement(resultingFunction,i);
                resultingFunction.clear();
            }
        }
    }

    //functions
    public int firstFunction(double x)
    {
        return (int) Math.pow(x,2);
    } //f(x) = x²
    public int secondFunction(double x)
    {
        return (int) Math.pow(x-2,2);
    }//f(x) = (x-2)²
    public int getNumberOfFunctions() {
        return numberOfFunctions;
    }


    //Getters and Setters
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



}
