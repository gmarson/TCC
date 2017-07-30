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
    public static ScalarizeWS scalarizationApproach = new ScalarizeWS();


    public Front pareto = new Front();

    public void runAlgorithm(Problem problem)
    {
        moeadPopulation.population = problem.generateMembers(Constants.POPULATION_SIZE);
        problem.evaluateAgainstObjectiveFunctions(moeadPopulation);
        instantiateWeightVectors();
        scalarizationApproach.calculateSolutionForPopulation(moeadPopulation);

        MOEADFunctions.NeighborhoodSettings.setNeighboursForAllMembers(moeadPopulation);

        MOEADFunctions.mainLoop(moeadPopulation,problem);

        saveParetto();

        Printer.printNeighborhoods(moeadPopulation);//todo
        Printer.printBinaryMembersWithAppliedFunctions(nonDominatedPopulation);//todo
        reset();
    }


    private void instantiateWeightVectors(){
        for(Member member : moeadPopulation.population){
            member.weightVector = new WeightVector();
        }
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
