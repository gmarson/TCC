package ManyObjective.MOEADFunctions;

import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighboring;

import java.util.ArrayList;


/**
 * Created by gabrielm on 06/05/17.
 */
public class OffspringGeneration {

    private static ArrayList<Member> generateChildrenGivenNeighboring(ArrayList<Member> parentNeighboring, Problem problem){

        Population parentsPopulation = SelectionNeighboring.selectParents(parentNeighboring);
        Population childPopulation = problem.crossover.crossoverAndMutation(parentsPopulation);
        problem.evaluateAgainstObjectiveFunctions(childPopulation);

        return childPopulation.population;
    }


    public static void updateNeighboring(Population population, Problem problem){

        for (int i = 0; i < population.population.size(); i++)
        {
            Member cell = population.population.get(i);
            ArrayList<Member> children = generateChildrenGivenNeighboring(cell.closestMembers , problem);

            addToMOEADNonDominatedPopulation(children.get(0).deepCopy());
            insertion(cell,children.get(0));
        }

    }

    private static void insertion(Member cell, Member child){
        insertionForNeighborhood(cell,child);
    }

    public static int counter =0;
    private static void insertionForNeighborhood(Member cell, Member child) {

        for (int i = 0; i < cell.closestMembers.size(); i++)
        {
            Member neighborhoodMember = cell.closestMembers.get(i);

            calculateSolutions(neighborhoodMember,child);

            if (neighborhoodMember.solution > child.solution)
            {
                counter++;
                cell.closestMembers.set(i,child.deepCopy());

            }
        }
    }

    private static void calculateSolutions(Member memberInsideCell, Member child){
        child.weightVector = memberInsideCell.weightVector;
        SolutionWeightedSum.calculateSolution(child);
    }

    private static void addToMOEADNonDominatedPopulation(Member member){
        member.solution = -1.0;
        member.weightVector = null;
        member.closestMembers = null;
        MOEAD.nonDominatedPopulation.addMember(member);
    }



}
