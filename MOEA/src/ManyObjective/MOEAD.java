package ManyObjective;

import Constants.Constants;
import ManyObjective.MOEADFunctions.*;
import Population.*;
import Problems.Problem;
import Utilities.Printer;
import Fronts.*;
import java.util.ArrayList;
import java.util.Scanner;


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


        nonDominatedPopulation.population = new ArrayList<Member>(p.population);

        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            OffspringGeneration.updateNeighboring(p,problem);

            nonDominatedPopulation.fastNonDominatedSort();
            nonDominatedPopulation.population = nonDominatedPopulation.fronts.allFronts.get(0).membersAtThisFront;

            genCounter++;

        }



        saveParetto(problem);
        Population aux = new Population();
        aux.population = paretto.membersAtThisFront;
        Scanner s = new Scanner(System.in);

            //Printer.printMembersValue(aux);//todo
            //Printer.printNeighboring(p);//todo
            //s.nextLine();




        //Printer.printMembersWithBinaryValue(aux);//todo
        //Printer.printMembersWithAppliedFunctions(aux);//todo
        reset();
    }


    private void reset(){
        p = new Population();
        genCounter = 0;
        nonDominatedPopulation = new Population();
    }

    private void saveParetto(Problem problem){
        Problem.removeSimilar(nonDominatedPopulation.getFirstFront(),problem);
        paretto = new Front();
        paretto = nonDominatedPopulation.fronts.allFronts.get(0);
    }



}
