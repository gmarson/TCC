package ManyObjective;

import Constants.Constants;
import ManyObjective.MOEADFunctions.*;
import Population.*;
import Problems.Problem;
import Utilities.Printer;
import Fronts.*;
import Utilities.Utils;
/**
 * Created by gabrielm on 01/04/17.
 */
public class MOEAD {

    public static Population nonDominatedPopulation = new Population();
    private static Population moeadPopulation = new Population();
    private int genCounter = 0;
    public Front paretto = new Front();

    public void runAlgorithm(Problem problem)
    {
        moeadPopulation.population = problem.generateMembers(Constants.POPULATION_SIZE );
        Neighboring.createWeightVectorForPopulation(moeadPopulation);

        problem.evaluateAgainstObjectiveFunctions(moeadPopulation);

        SolutionWeightedSum.calculateSolutionForPopulation(moeadPopulation);
        Neighboring.setNeighboursForAllMembers(moeadPopulation);

        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            System.out.println("GEN = "+genCounter);//todo

            OffspringGeneration.updateNeighboring(moeadPopulation,problem);

            nonDominatedPopulation.removeAllButNonDominated();

            genCounter++;
        }

        saveParetto();

        //Printer.printNeighboring(moeadPopulation);//todo
        Printer.printBinaryMembersWithAppliedFunctions(nonDominatedPopulation);//todo

        reset();
    }


    private void reset(){
        moeadPopulation = new Population();
        genCounter = 0;
        nonDominatedPopulation = new Population();
    }

    private void saveParetto(){
        paretto = new Front();
        paretto.membersAtThisFront = nonDominatedPopulation.population;
    }

}
