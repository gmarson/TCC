package ManyObjective.MOEADFunctions;

import Constants.Constants;
import Dominance.Dominance;
import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighborhood;
import Utilities.Utils;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;

/**
 * Created by gabrielm on 06/05/17.
 */
public class MOEADFunctions {

    public static int genCounter = 0;

    private static Member generateChildrenGivenNeighborhood(ArrayList<Member> parentNeighborhood, Problem problem){

        Population parentsPopulation = SelectionNeighborhood.selectParents(parentNeighborhood);

        Population childPopulation = problem.crossover.crossoverAndMutation(parentsPopulation);

        problem.evaluateAgainstObjectiveFunctions(childPopulation);

        return childPopulation.population.get(Utils.getRandom(0,2));
    }

    public static void mainLoop(Population population, Problem problem){

        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            System.out.println("GEN = "+genCounter);//todo

            for (Member cell: population.population){

                Member child = generateChildrenGivenNeighborhood(cell.neighborhood, problem);
                addToNonDominatedPopulation(child, problem);
                updateCell(cell,child);
                updateNeighborhood(cell.neighborhood,child);
            }


            genCounter++;
        }
    }

    private static void updateCell(Member cell, Member child){

        MOEAD.scalarization.calculateFitness(child,cell.weightVector.vector);
        if(child.fitness < cell.fitness){
            replaceMember(cell,child);
        }
    }

    private static void updateNeighborhood(ArrayList<Member> neighborhood, Member child) {

        for(Member neighborhoodMember : neighborhood){

            MOEAD.scalarization.calculateFitness(child,neighborhoodMember.weightVector.vector);

            if(child.fitness < neighborhoodMember.fitness){
                replaceMember(neighborhoodMember,child);
            }
        }
    }

    private static void replaceMember(Member neighborhoodMember, Member child){
        neighborhoodMember.binaryValue = child.binaryValue;
        neighborhoodMember.resultOfFunctions = child.resultOfFunctions;
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

        if (shouldAddNewMember){
            MOEAD.archive.addMember(member);
            MOEAD.archive.population.removeAll(toBeRemoved);
        }
    }


    public static class NeighborhoodSettings {

        public static void setNeighboursForAllMembers(Population population) {

            for (int i = 0; i < population.population.size(); i++) {
                Member cell = population.population.get(i);

                for (int j = i; j < population.population.size(); j++) {
                    Member child = population.population.get(j);
                    double distance = Utils.euclideanDistanceBasedOnWeightVector(cell,child);

                    if (distance != 0.0){
                        addOrdered(child,cell,distance);
                        addOrdered(cell,child,distance);
                    }
                }
            }
        }

        private static void addOrdered(Member candidate, Member cell, double candidateDistance){

            if (cell.neighborhood.isEmpty())
                cell.neighborhood.add(candidate);
            else {
                int i;

                for (i = 0; i < cell.neighborhood.size(); i++) {

                    if (candidateDistance < Utils.euclideanDistanceBasedOnWeightVector(cell.neighborhood.get(i),cell)) {
                        break;
                    }
                }

                cell.neighborhood.add(i,candidate);

                if (cell.neighborhood.size() > Constants.NEIGHBOURHOOD_SIZE) cell.neighborhood.remove(cell.neighborhood.size() -1 );
            }
        }
    }

}
