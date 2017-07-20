package ManyObjective.MOEADFunctions;

import Population.*;

import java.util.Scanner;

/**
 * Created by gabrielm on 30/04/17.
 */
public class SolutionWeightedSum {


    public static void calculateSolutionForPopulation(Population population){
        for (Member member : population.population){
            calculateSolution(member, member.weightVector.vector);
        }
    }

    static void calculateSolution(Member member, double[] weightVector){
        member.solution = 0.0;

        for (int i = 0; i < member.resultOfFunctions.size(); i++) {
            member.solution += (member.resultOfFunctions.get(i) * weightVector[i]);
        }
    }

}
