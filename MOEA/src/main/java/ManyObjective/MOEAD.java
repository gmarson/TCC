package ManyObjective;

import SupportingFiles.Parameters;
import ManyObjective.MOEADFunctions.*;
import Population.*;
import Problems.Problem;
import SupportingFiles.Matrix;
import SupportingFiles.Printer;
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
        moeadPopulation.population = problem.generateMembers(Parameters.POPULATION_SIZE);
        instantiateVariables();
        problem.evaluateAgainstObjectiveFunctions(moeadPopulation);

        MOEADFunctions.neighborhoods = new Matrix(Parameters.POPULATION_SIZE, Parameters.NEIGHBOURHOOD_SIZE, moeadPopulation);
        MOEADFunctions.NeighborhoodSettings.initializeNeighborhoods(moeadPopulation.population);
        MOEADFunctions.transferToMatrix(moeadPopulation);

        //MOEADFunctions.NeighborhoodSettings.setNeighboursForAllMembers();

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
