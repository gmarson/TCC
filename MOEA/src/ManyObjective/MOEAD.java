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
    private Population moeadPopulation = new Population();
    private int genCounter = 0;
    public Front paretto = new Front();

    public void runAlgorithm(Problem problem)
    {
        moeadPopulation.population = problem.generateMembers(Constants.POPULATION_SIZE);
        Neighboring.createWeightVectorForPopulation(moeadPopulation);

        problem.evaluateAgainstObjectiveFunctions(moeadPopulation);
        SolutionWeightedSum.calculateSolutionForPopulation(moeadPopulation);
        Neighboring.setNeighboursForAllMembers(moeadPopulation);
        populateNonDominatedPopulation(problem);

        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            System.out.println("GEN = "+genCounter);//todo

            OffspringGeneration.updateNeighboring(moeadPopulation,problem);

            nonDominatedPopulation.removeAllButNonDominated();

            Problem.removeSimilar(nonDominatedPopulation,problem);

            genCounter++;
        }

        saveParetto();

        Population aux = new Population();
        aux.population = paretto.membersAtThisFront;


        //System.out.println("Tamanho: "+aux.population.size());//todo
        //Printer.printMembersValue(aux);//todo
        Printer.printBinaryMembersWithAppliedFunctions(aux);//todo
        //Printer.printMembersWithAppliedFunctions(aux);//todo

        reset();
    }

    private void populateNonDominatedPopulation(Problem problem) {
        for (Member member:moeadPopulation.population)
        {
            if (!Problem.valueOfMemberIsPresent(member,nonDominatedPopulation,problem))
                nonDominatedPopulation.addMember(member.deepCopy());
        }
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
