package ManyObjective;

import Constants.Constants;
import ManyObjective.MOEADFunctions.*;
import Population.*;
import Problems.Problem;
import Utilities.Printer;
import Fronts.*;

/**
 * Created by gabrielm on 01/04/17.
 */
public class MOEAD {

    public static Population nonDominatedPopulation = new Population();
    private static Population moeadPopulation = new Population();

    public Front pareto = new Front();

    public void runAlgorithm(Problem problem)
    {
        moeadPopulation.population = problem.generateMembers(Constants.POPULATION_SIZE);
        problem.evaluateAgainstObjectiveFunctions(moeadPopulation);

        ScalarizeWeightedSum.calculateSolutionForPopulation(moeadPopulation);

        MOEADFunctions.Neighboring.setNeighboursForAllMembers(moeadPopulation);

        MOEADFunctions.mainLoop(moeadPopulation,problem);

        saveParetto();

        Printer.printNeighboring(moeadPopulation);//todo
        Printer.printBinaryMembersWithAppliedFunctions(nonDominatedPopulation);//todo
        reset();
    }


    private void reset(){
        moeadPopulation = new Population();
        MOEADFunctions.genCounter = 0;
        nonDominatedPopulation = new Population();
    }

    private void saveParetto(){
        pareto = new Front();
        pareto.membersAtThisFront = nonDominatedPopulation.population;
    }

}
