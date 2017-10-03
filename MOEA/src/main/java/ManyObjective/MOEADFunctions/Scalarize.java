package ManyObjective.MOEADFunctions;

import Population.*;
import SupportingFiles.Matrix;

/**
 * Created by gabrielm on 30/07/17.
 * Project : TCC.
 */
public abstract class Scalarize {

    abstract void calculateSolutionForPopulation(Matrix neighborhoods);
    abstract void calculateFitness(Member member, Member neighborhoodMember);

}
