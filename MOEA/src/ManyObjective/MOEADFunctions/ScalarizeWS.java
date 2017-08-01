package ManyObjective.MOEADFunctions;

import Constants.Constants;
import Population.*;

import java.util.Scanner;

/**
 * Created by gabrielm on 30/04/17.
 */
public class ScalarizeWS extends Scalarize {

    @Override
    public void calculateSolutionForPopulation(Population population){
        for (Member member : population.population){
            calculateSolution(member, member.weightVector.vector);
        }
    }

    @Override
    void calculateSolution(Member member, double[] weightVector){

        member.fitness = 0.0;


        for (int i = 0; i < member.resultOfFunctions.size(); i++) {
            member.fitness += (member.resultOfFunctions.get(i) * weightVector[i]);

        }

    }

}
