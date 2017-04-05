package SPEA2;

import Constants.Constants;
import Population.*;
import Utilities.*;
import Selections.*;
import Problems.*;

import java.util.Scanner;

/** 
 * Created by gabrielm on 09/01/17.
 */
public class SPEA2 {

    Scanner s = new Scanner(System.in);

    public void runAlgorithm(Problem problem){
        int genCounter = 0;

        Population p = new Population();
        Population archive = new Population();
        Population union = new Population();
        Population selected;
        Selection selectedFitness = new SelectionArchive();

        p.population = problem.generateMembers(Constants.POPULATION_SIZE);

        while(genCounter < Constants.NUMBER_OF_GENERATIONS)
        {
            System.out.println("GERACAO = "+ genCounter+"===========================================");//todo

            union.mergeTwoPopulations(p,archive);

            problem.evaluateAgainstObjectiveFunctions(union);

            union.fastNonDominatedSort();

            Fitness.calculateFitness(union);

            archive = EnvironmentalSelection.environmentalSelection(p,archive);

            selected = selectedFitness.selectParents(archive);

            p = problem.crossover.crossoverAndMutation(selected);

            genCounter++;
            Fitness.prepareForNextGen();

        }

        problem.printResolutionMessage();
        Printer.printMembersWithValues(archive); //todo
        
    }


}
