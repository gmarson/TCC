package ManyObjective.MOEADFunctions;

import Population.*;
import SupportingFiles.Matrix;

/**
 * Created by gabrielm on 30/04/17.
 */
public class ScalarizeWS extends Scalarize {

    @Override
    public void calculateSolutionForPopulation(Matrix neighborhoods){

        for (int i = 0; i < neighborhoods.rows; i++) {
            Member member = neighborhoods.memberMatrix[i][0];

            for (int j = 0; j < member.resultOfFunctions.size(); j++) {
                member.fitness += (member.resultOfFunctions.get(j) * member.weightVector.vector[j]);
            }
        }
    }

    @Override
    void calculateFitness(Member member, Member neighborhoodMember){
        member.fitness = 0.0;

        for (int i = 0; i < member.resultOfFunctions.size(); i++) {
            member.fitness += (member.resultOfFunctions.get(i) * neighborhoodMember.weightVector.vector[i]);
        }

    }

}
