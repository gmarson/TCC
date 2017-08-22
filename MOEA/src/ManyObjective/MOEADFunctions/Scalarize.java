package ManyObjective.MOEADFunctions;

import Population.*;

/**
 * Created by gabrielm on 30/07/17.
 * Project : TCC.
 */
public abstract class Scalarize {

    abstract void calculateSolutionForPopulation(Population population);
    abstract void calculateFitness(Member member, double[] weightVector);

}
