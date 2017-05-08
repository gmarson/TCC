package ManyObjective;

import Constants.Constants;
import ManyObjective.MOEADFunctions.*;
import Population.Population;
import Problems.Problem;
import Utilities.Printer;
import Utilities.ProgressBar;


/**
 * Created by gabrielm on 01/04/17.
 */
public class MOEAD {

    public static Population nonDominatedPopulation;
    Population p = new Population();
    private int genCounter = 0;


    public void runAlgorithm(Problem problem)
    {
        p.population = problem.generateMembers(Constants.POPULATION_SIZE);
        Neighboring.createWeightVectorForPopulation(p);
        problem.evaluateAgainstObjectiveFunctions(p);
        SolutionWeightedSum.calculateSolutionForPopulation(p);
        nonDominatedPopulation = new Population(p);

        Neighboring.setNeighboursOfAllMembers(p);
        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            OffspringGeneration.updateNeighboring(p,problem);
            nonDominatedPopulation.fastNonDominatedSort();
            nonDominatedPopulation.population = nonDominatedPopulation.fronts.allFronts.get(0).membersAtThisFront;
            genCounter++;
            System.out.print(genCounter+" ");//todo

        }

        Printer.printMembersWithAppliedFunctions(nonDominatedPopulation);//todo
    }

    private void reset(){


    }

    private void saveParetto(Problem problem){


        //Problem.removeSimilar(paretto,problem);
    }



}
