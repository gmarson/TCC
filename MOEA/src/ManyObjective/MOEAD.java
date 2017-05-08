package ManyObjective;

import Constants.Constants;
import Fronts.Front;
import ManyObjective.MOEADFunctions.*;
import Population.Population;
import Problems.Problem;
import Utilities.Printer;
import Utilities.ProgressBar;


/**
 * Created by gabrielm on 01/04/17.
 */
public class MOEAD {

    public static Population nonDominatedPopulation = new Population();
    Population p = new Population();
    private int genCounter = 0;
    public Front paretto = new Front();


    public void runAlgorithm(Problem problem)
    {
        p.population = problem.generateMembers(Constants.POPULATION_SIZE);
        Neighboring.createWeightVectorForPopulation(p);
        problem.evaluateAgainstObjectiveFunctions(p);
        SolutionWeightedSum.calculateSolutionForPopulation(p);
        Neighboring.setNeighboursOfAllMembers(p);
        nonDominatedPopulation = new Population(p);

        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            OffspringGeneration.updateNeighboring(p,problem);

            nonDominatedPopulation.fastNonDominatedSort();
            nonDominatedPopulation.population = nonDominatedPopulation.fronts.allFronts.get(0).membersAtThisFront;

            genCounter++;
            System.out.print(genCounter+" ");//todo

        }


        System.out.println();

        saveParetto(problem);
        Printer.printMembersWithAppliedFunctions(nonDominatedPopulation);//todo

        reset();
    }

    private void reset(){
        nonDominatedPopulation = new Population();
        p = new Population();
        genCounter = 0;

    }

    private void saveParetto(Problem problem){
        paretto = new Front();
        Problem.removeSimilar(nonDominatedPopulation.fronts.allFronts.get(0),problem);
        paretto.membersAtThisFront = nonDominatedPopulation.fronts.allFronts.get(0).membersAtThisFront;

    }



}
