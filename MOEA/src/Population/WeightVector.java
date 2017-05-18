package Population;

import Constants.Constants;
import Utilities.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 13/05/17.
 */
public class WeightVector{


    public double[] vector;
    private double TOTAL_SUM = 1.0;

    public WeightVector(){
        vector = this.generateWeightVector();
    }

    private double[] generateWeightVector(){
        ArrayList<Double> numbers = new ArrayList<>();

        for (int i = 0; i < Constants.PROBLEM_SIZE - 1; i++) {

            Utils.insertDataOnCrescentOrderedArray(Utils.getRandomDouble(0.0,1.0),numbers);
        }
        numbers.add(0,0.0);
        numbers.add(numbers.size(),1.0);

        double[] weightVector = new double[Constants.PROBLEM_SIZE];
        for (int i = 0; i < numbers.size() - 1; i++) {
            weightVector[i] = numbers.get(i+1) - numbers.get(i);
        }

        return weightVector;
    }


}
