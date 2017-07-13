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

    private static ArrayList<Member> generateChildGivenNeighboring(ArrayList<Member> parentNeighboring, Problem problem){

        Population parentsPopulation = SelectionNeighboring.selectParents(parentNeighboring);
        Population childPopulation = problem.crossover.crossoverAndMutation(parentsPopulation);
        problem.evaluateAgainstObjectiveFunctions(childPopulation);

        return childPopulation.population;
    }


    public static void updateNeighboring(Population population, Problem problem){

        for (int i = 0; i < population.population.size(); i++)
        {
            Member cell = population.population.get(i);
            ArrayList<Member> children = generateChildGivenNeighboring (cell.closestMembers , problem);

            insertion(cell,children.get(0),population,i);
            insertion(cell,children.get(1),population,i);
        }

    }

    private static void insertion(Member cell, Member child, Population population,int indexOf){
        calculateSolutions(cell,child);
        if (cell.solution < child.solution)
        {
            replaceMember(cell,child.deepCopy(),population,indexOf);
            addToMOEADNonDominatedPopulation(child.deepCopy());
        }

        insertionForNeighborhood(cell,child);
    }

    private static void insertionForNeighborhood(Member cell, Member child) {

        for (int i = 0; i < cell.closestMembers.size(); i++)
        {
            Member neighborhoodMember = cell.closestMembers.get(i);

            calculateSolutions(neighborhoodMember,child);
            if (neighborhoodMember.solution < child.solution)
            {
                cell.closestMembers.set(i,child.deepCopy());
                addToMOEADNonDominatedPopulation(child.deepCopy());
            }
        }
    }

    private static void replaceMember(Member cell, Member child, Population population,int indexOf) {
        child.closestMembers = cell.closestMembers;
        population.population.set(indexOf,child);
        cell = null;

    }

    private static void calculateSolutions(Member cell, Member child){
        child.weightVector = cell.weightVector;
        SolutionWeightedSum.calculateSolution(child);

    }

    private static void addToMOEADNonDominatedPopulation(Member member){
        member.solution = -1.0;
        member.weightVector = null;
        member.closestMembers = null;
        MOEAD.nonDominatedPopulation.addMember(member);
    }



}
