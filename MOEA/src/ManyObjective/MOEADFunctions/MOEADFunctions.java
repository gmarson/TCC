package ManyObjective.MOEADFunctions;

import SupportingFiles.Parameters;
import Dominance.Dominance;
import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighborhood;
import SupportingFiles.Matrix;
import SupportingFiles.Utils;

import java.util.ArrayList;

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
//
//        if(d.dominates(childPopulation.population.get(0), childPopulation.population.get(1))){
//            return childPopulation.population.get(0);
//        }
//
//        return childPopulation.population.get(1);

        return childPopulation.population.get(Utils.getRandom(0,2));
    }

    public static void mainLoop(Problem problem){

        while (genCounter < Parameters.NUMBER_OF_GENERATIONS){

            System.out.println("GEN = "+genCounter);//todo

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


    public static class NeighborhoodSettings {

        public static void setNeighboursForAllMembers() {

            for (int i = 0; i < neighborhoods.rows ; i++) {
                for (int j = i; j < neighborhoods.rows; j++) {

                    if(i != j) {
                        addOrdered(i, j);
                        addOrdered(j, i);
                    }
                }
            }
        }

        private static void addOrdered(int cellIndex, int neighbourCandidateIndex){

            boolean shouldAddLastPosition = false;
            Member[] neighborhood = neighborhoods.memberMatrix[cellIndex];
            Member cell = neighborhood[0];
            Member child = neighborhoods.memberMatrix[neighbourCandidateIndex][0];
            double candidateDistance = Utils.euclideanDistanceBasedOnWeightVector(cell,child);

            int indexToInsert;
            for (indexToInsert = 1; indexToInsert < Parameters.NEIGHBOURHOOD_SIZE ; indexToInsert++) {

                if (neighborhood[indexToInsert] != null){
                    if (candidateDistance < Utils.euclideanDistanceBasedOnWeightVector(neighborhood[indexToInsert],neighborhood[0])){
                        shouldAddLastPosition = true;
                        break;
                    }
                }
                else
                {
                    neighborhood[indexToInsert] = child;
                    return;
                }
            }

            for (int i = Parameters.NEIGHBOURHOOD_SIZE -1; i > indexToInsert ; i--) {
                neighborhood[i] = neighborhood[i - 1];
            }

            if(shouldAddLastPosition) neighborhood[indexToInsert] = child;

        }
    }

}
