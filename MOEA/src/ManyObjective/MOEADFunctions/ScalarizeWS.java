package ManyObjective.MOEADFunctions;

import Population.*;
import Utilities.Matrix;

/**
 * Created by gabrielm on 30/04/17.
 */
public class ScalarizeWS extends Scalarize {

    @Override
    public void calculateSolutionForPopulation(Matrix neighborhoods){

        for (int i = 0; i < neighborhoods.rows; i++) {
            calculateFitness(neighborhoods.memberMatrix[i][0],neighborhoods.memberMatrix[i][0].weightVector.vector);
        }
    }

    @Override
    void calculateFitness(Member member, double[] weightVector){
        member.fitness = 0.0;

        for (int i = 0; i < member.resultOfFunctions.size(); i++) {
            member.fitness += (member.resultOfFunctions.get(i) * weightVector[i]);
        }
    }

}
