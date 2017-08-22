package ManyObjective.MOEADFunctions;

import Population.*;

/**
 * Created by gabrielm on 30/04/17.
 */
public class ScalarizeWS extends Scalarize {

    @Override
    public void calculateSolutionForPopulation(Population population){
        for (Member member : population.population){
            calculateFitness(member, member.weightVector.vector);
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
