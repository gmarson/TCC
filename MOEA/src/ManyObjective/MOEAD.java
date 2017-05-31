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
    Population moeadPopulation = new Population();
    private int genCounter = 0;
    public Front paretto = new Front();


    public void runAlgorithm(Problem problem)
    {
        moeadPopulation.population = problem.generateMembers(Constants.POPULATION_SIZE);

        Neighboring.createWeightVectorForPopulation(moeadPopulation);
        problem.evaluateAgainstObjectiveFunctions(moeadPopulation);
        SolutionWeightedSum.calculateSolutionForPopulation(moeadPopulation);
        Neighboring.setNeighboursOfAllMembers(moeadPopulation);

        populateNonDominatedPopulation();

        //TODO vc melhorou bastante o alg mas ainda falta algo pra melhorar mais ainda o desempenho dele,
        //todo tem algum erro escodido por ai , vamos achá-lo amanhã !
        //todo continue fazendo o q vc ta fazendo que acho que da bom
        //todo se nao der, é só explorar outras frentes!
        //todo EU HEI DE VENCER!

        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            OffspringGeneration.updateNeighboring(moeadPopulation,problem);

            nonDominatedPopulation.fastNonDominatedSort();
            Problem.removeSimilar(nonDominatedPopulation.fronts.allFronts.get(0),problem);
            nonDominatedPopulation.population = nonDominatedPopulation.fronts.allFronts.get(0).membersAtThisFront;

            genCounter++;

        }

        saveParetto(problem);
        Population aux = new Population();
        aux.population = paretto.membersAtThisFront;

        Scanner s = new Scanner(System.in);



        Printer.printMembersValue(aux);//todo

        /*if (aux.population.size() < 3)
        {
            System.out.println("imprimindo nondominatedpopulation");
            Printer.printMembersValue(nonDominatedPopulation);

            System.out.println("imprimindo p");
            for (Member member : moeadPopulation.population)
            {
                System.out.println(member.value);
            }

            System.out.println("Resultado ");

            Printer.printNeighboring(moeadPopulation);//todo
            s.nextLine();//todo
        }*/
        //Printer.printNeighboring(moeadPopulation);//todo
        //s.nextLine();

        //Printer.printMembersWithBinaryValue(aux);//todo
        //Printer.printMembersWithAppliedFunctions(aux);//todo
        reset();
    }

    private void populateNonDominatedPopulation() {
        for (Member member:moeadPopulation.population)
        {
            nonDominatedPopulation.addMember(member.deepCopy());
        }

    }


    private void reset(){
        moeadPopulation = new Population();
        genCounter = 0;
        nonDominatedPopulation = new Population();
    }

    private void saveParetto(Problem problem){

        Problem.removeSimilar(nonDominatedPopulation.getFirstFront(),problem);
        paretto = new Front();
        paretto = nonDominatedPopulation.fronts.allFronts.get(0);

    }



}
