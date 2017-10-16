package ManyObjective.MOEADFunctions;

import SupportingFiles.Parameters;
import Dominance.Dominance;
import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighborhood;
import SupportingFiles.Matrix;
import SupportingFiles.Utils;


import org.apache.commons.math3.util.MathArrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by gabrielm on 06/05/17.
 */
public class MOEADFunctions {

    public static Matrix neighborhoods;
    public static int genCounter = 0;

    private static Member generateChildrenGivenNeighborhood(Member[] neighborhood, Problem problem){
        Dominance d = new Dominance();

        Population parentsPopulation = SelectionNeighborhood.selectParents(neighborhood);

        Population childPopulation = problem.crossover.crossoverAndMutation(parentsPopulation);

        problem.evaluateAgainstObjectiveFunctions(childPopulation);

        if(d.dominates(childPopulation.population.get(0), childPopulation.population.get(1))){
            return childPopulation.population.get(0);
        }

        return childPopulation.population.get(1);

        //return childPopulation.population.get(Utils.getRandom(0,2));
    }

    public static void mainLoop(Problem problem){

        while (genCounter < Parameters.NUMBER_OF_GENERATIONS){

            //System.out.println("GEN = "+genCounter);//todo

            for (int i = 0; i < Parameters.POPULATION_SIZE; i++) {
                Member[] neighborhood = neighborhoods.memberMatrix[i];
                Member child = generateChildrenGivenNeighborhood(neighborhood, problem);
                addToNonDominatedPopulation(child.deepCopy(),problem);

                updateNeighborhood(neighborhood, child);
            }

            genCounter++;
        }
    }

    private static void updateNeighborhood(Member[] neighborhood, Member child) {

        for (int i = 0; i < Parameters.NEIGHBOURHOOD_SIZE; i++) {
            Member neighborhoodMember = neighborhood[i];

            MOEAD.scalarization.calculateFitness(child,neighborhoodMember);

            if(child.fitness <= neighborhoodMember.fitness ){
                replaceMember(neighborhoodMember,child);
                break;
            }
        }
    }

    private static void replaceMember(Member neighborhoodMember, Member child){
        neighborhoodMember.binaryValue = new ArrayList<>(child.binaryValue);
        neighborhoodMember.resultOfFunctions = new ArrayList<>(child.resultOfFunctions);
        neighborhoodMember.value = child.value;
        neighborhoodMember.fitness = child.fitness;
    }

    private static void addToNonDominatedPopulation(Member member, Problem problem){
        Dominance dominance = new Dominance();
        ArrayList<Member> toBeRemoved = new ArrayList<>();
        boolean shouldAddNewMember = true;

        if (Problem.valueOfMemberIsPresent(member,MOEAD.archive,problem)) return;

        for (Member paretoMember : MOEAD.archive.population){

            if (dominance.dominates(member,paretoMember)){
                toBeRemoved.add(paretoMember);
            }

            if (dominance.dominates(paretoMember,member)){
                shouldAddNewMember = false;
            }
        }

        MOEAD.archive.population.removeAll(toBeRemoved);
        if (shouldAddNewMember){
            MOEAD.archive.addMember(member);
        }
    }

    public static void transferToMatrix(Population moeadPopulation) {
        for (int i = 0; i < moeadPopulation.population.size(); i++) {
            Member currentMember = moeadPopulation.population.get(i);
            for (int j = 0; j < Parameters.NEIGHBOURHOOD_SIZE; j++) {
                MOEADFunctions.neighborhoods.memberMatrix[i][j] = currentMember.neighborhood.get(j);
            }
        }
    }

    public static class NeighborhoodSettings {
        public static void initializeNeighborhoods(ArrayList<Member> population) {
            List<Member> sortedPopulation = new ArrayList<Member>(population);

            for (Member individual : population) {
                Collections.sort(sortedPopulation, new WeightSorter(individual));

                for (int i = 0; i < Parameters.NEIGHBOURHOOD_SIZE; i++) {

                    individual.addNeighbor(sortedPopulation.get(i));
                }
            }
        }

        private static class WeightSorter implements Comparator<Member> {

            private final Member individual;

            WeightSorter(Member individual) {
                this.individual = individual;
            }

            @Override
            public int compare(Member o1, Member o2) {
                double d1 = MathArrays.distance(
                        individual.weightVector.vector, o1.weightVector.vector);
                double d2 = MathArrays.distance(
                        individual.weightVector.vector, o2.weightVector.vector);

                return Double.compare(d1, d2);
            }

        }


    }





}
