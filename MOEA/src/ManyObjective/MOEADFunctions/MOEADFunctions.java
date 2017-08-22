package ManyObjective.MOEADFunctions;

import Constants.Constants;
import Dominance.Dominance;
import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighborhood;
import Utilities.Utils;

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
                addToNonDominatedPopulation(child.deepCopy(), problem);
                //updateInCell(cell,child);
                updateNeighborhood(cell,child);
            }

            genCounter++;
        }
    }

    private static void updateInCell(Member cell, Member child){
        if(child.fitness < cell.fitness){
            replaceMember(cell,child);
        }
    }

    private static void updateNeighborhood(Member cell, Member child) {

        for(Member neighborhoodMember : cell.neighborhood){

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

        MOEAD.archive.population.removeAll(toBeRemoved);
        if (shouldAddNewMember) MOEAD.archive.addMember(member);
    }


    public static class NeighborhoodSettings {

        public static void setNeighboursForAllMembers(Population population) {

            for (Member cell: population.population){
                cell.neighborhood = new ArrayList<>();

                for (Member child: population.population){
                    child.distanceFromParentMember = Utils.euclideanDistanceBasedOnWeightVector(cell,child);
                    addOrdered(child,cell.neighborhood);
                }
            }

        }

        private static void addOrdered(Member memberToBeInserted, ArrayList<Member> neighborhood){

            if (neighborhood.isEmpty())
                neighborhood.add(memberToBeInserted);
            else {
                int i;

                for (i = 0; i < neighborhood.size(); i++) {
                    if (memberToBeInserted.distanceFromParentMember < neighborhood.get(i).distanceFromParentMember) {
                        break;
                    }
                }

                neighborhood.add(i,memberToBeInserted);

                if (neighborhood.size() > Constants.NEIGHBOURHOOD_SIZE) neighborhood.remove(neighborhood.size() -1 );

            }
        }
    }

}
