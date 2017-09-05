package Population;

import SupportingFiles.Constants;
import SupportingFiles.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gabrielm on 13/05/17.
 */
public class WeightVector implements Serializable{


    public double[] vector;
    private double TOTAL_SUM = 1.0;

    public WeightVector(){
        vector = this.generateWeightVector();
    }

    WeightVector(WeightVector wv)
    {
        this.vector = wv.vector;
        this.TOTAL_SUM = wv.TOTAL_SUM;
    }


    private double[] generateWeightVectorNormalizing(){
        double[] weightVector = new double[Constants.PROBLEM_SIZE];
        double sum=0;

        for (int i = 0; i < Constants.PROBLEM_SIZE; i++) {
            weightVector[i] = Utils.getRandomDouble(0,1000);
            sum += weightVector[i];
        }



        for (int i = 0; i <Constants.PROBLEM_SIZE ; i++) {
            weightVector[i] /= sum;
        }

        return weightVector;

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
