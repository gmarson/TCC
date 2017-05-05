package ManyObjective.MOEADFunctions;

import Constants.Constants;
import Population.*;
import Utilities.Utils;

/**
 * Created by gabrielm on 30/04/17.
 */
public class ScalarOptimization {

    public static void weightedSumApproach(Population p){
        int i =0;
        for(Member member : p.population){
            member.solution += (member.weightVector[i]* member.resultOfFunctions.get(i));
            i++;
        }
    }

    private static void tchebycheffApproach(Population p){

    }

}
