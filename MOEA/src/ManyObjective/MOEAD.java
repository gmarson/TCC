package ManyObjective;

import Constants.Constants;
import ManyObjective.MOEADFunctions.*;
import Population.*;
import Problems.Problem;
import Utilities.Printer;
import Utilities.ProgressBar;

import java.util.ArrayList;


/**
 * Created by gabrielm on 01/04/17.
 */
public class MOEAD {

    public static Population nonDominatedPopulation = new Population();
    Population p = new Population();
    private int genCounter = 0;


    public void runAlgorithm(Problem problem)
    {
        p.population = problem.generateMembers(Constants.POPULATION_SIZE);
        Neighboring.createWeightVectorForPopulation(p);
        problem.evaluateAgainstObjectiveFunctions(p);
        SolutionWeightedSum.calculateSolutionForPopulation(p);
        Neighboring.setNeighboursOfAllMembers(p);



        nonDominatedPopulation.population = new ArrayList<Member>(p.population);

        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            OffspringGeneration.updateNeighboring(p,problem);

            nonDominatedPopulation.fastNonDominatedSort();
            nonDominatedPopulation.population = nonDominatedPopulation.fronts.allFronts.get(0).membersAtThisFront;

            genCounter++;


        }


        Problem.removeSimilar(nonDominatedPopulation.fronts.allFronts.get(0),problem);
        nonDominatedPopulation.population = nonDominatedPopulation.fronts.allFronts.get(0).membersAtThisFront;
        Printer.printMembersWithAppliedFunctions(nonDominatedPopulation);//todo
    }

    private void reset(){


    }

    private void saveParetto(Problem problem){


        //Problem.removeSimilar(paretto,problem);
    }



}
