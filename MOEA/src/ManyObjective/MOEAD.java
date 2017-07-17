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
        //Printer.printNeighboring(moeadPopulation);//todo
        //Utils.stop();//todo


        //populateNonDominatedPopulation(problem);

        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            System.out.println("GEN = "+genCounter);//todo

            OffspringGeneration.updateNeighboring(moeadPopulation,problem);

            //todo inserir ordenado aki
            nonDominatedPopulation.removeAllButNonDominated();

            Problem.removeSimilar(nonDominatedPopulation,problem);

            genCounter++;
        }

        System.out.println("Counter:"+ OffspringGeneration.counter);//todo
        saveParetto();

        Population aux = new Population();
        aux.population = paretto.membersAtThisFront;


        //System.out.println("Tamanho: "+aux.population.size());//todo
        //Printer.printMembersValue(aux);//todo
        Printer.printBinaryMembersWithAppliedFunctions(nonDominatedPopulation);//todo
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
