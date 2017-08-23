package ManyObjective;

import Constants.Constants;
import ManyObjective.MOEADFunctions.*;
import Population.*;
import Problems.Problem;
import Utilities.Printer;
import Fronts.*;

import java.util.ArrayList;

/**
 * Created by gabrielm on 01/04/17.
 */
public class MOEAD {

    public static Population archive = new Population();
    private static Population moeadPopulation = new Population();
    public static ScalarizeWS scalarization = new ScalarizeWS();

    public Front pareto = new Front();

    public void runAlgorithm(Problem problem)
    {
        moeadPopulation.population = problem.generateMembers(Constants.POPULATION_SIZE);
        problem.evaluateAgainstObjectiveFunctions(moeadPopulation);
        instantiateVariables();
        scalarization.calculateSolutionForPopulation(moeadPopulation);

        MOEADFunctions.NeighborhoodSettings.setNeighboursForAllMembers(moeadPopulation);

        MOEADFunctions.mainLoop(moeadPopulation,problem);

        saveParetto();

        Printer.printNeighborhoods(moeadPopulation);//todo
        Printer.printBinaryMembersWithAppliedFunctions(archive);//todo
        reset();
    }


    private void instantiateVariables(){
        for(Member member : moeadPopulation.population){
            member.weightVector = new WeightVector();
            member.neighborhood = new ArrayList<>();
        }
    }

    private void reset(){
        moeadPopulation = new Population();
        MOEADFunctions.genCounter = 0;
        archive = new Population();
    }

    private void saveParetto(){
        pareto = new Front();
        pareto.membersAtThisFront = archive.population;
    }

}
