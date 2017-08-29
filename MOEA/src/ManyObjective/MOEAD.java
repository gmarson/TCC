package ManyObjective;

import Constants.Constants;
import ManyObjective.MOEADFunctions.*;
import Population.*;
import Problems.Problem;
import Utilities.Matrix;
import Utilities.Printer;
import Fronts.*;

import java.util.ArrayList;

import static ManyObjective.MOEADFunctions.MOEADFunctions.neighborhoods;

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
        instantiateVariables();
        problem.evaluateAgainstObjectiveFunctions(moeadPopulation);
        MOEADFunctions.neighborhoods = new Matrix(Constants.POPULATION_SIZE, Constants.NEIGHBOURHOOD_SIZE, moeadPopulation);
        scalarization.calculateSolutionForPopulation(MOEADFunctions.neighborhoods);


        MOEADFunctions.NeighborhoodSettings.setNeighboursForAllMembers();
        MOEADFunctions.mainLoop(problem);

        saveParetto();

        //neighborhoods.printMatrix();//todo
        //Printer.printNeighborhoods(MOEADFunctions.neighborhoods);//todo
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
