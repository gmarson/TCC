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

    public static Member generateChildGivenNeighboring(ArrayList<Member> parentNeighboring, Problem problem){

        Population parentsPopulation = SelectionNeighboring.selectParents(parentNeighboring);
        Population childPopulation = problem.crossover.crossoverAndMutation(parentsPopulation);
        problem.evaluateAgainstObjectiveFunctions(childPopulation);
        childPopulation.fastNonDominatedSort();


        return childPopulation.population.get(0);
    }


    public static void updateNeighboring(Population population, Problem problem){

        for (int i = 0; i < population.population.size(); i++)
        {
            Member parent = population.population.get(i);
            Member childMember = generateChildGivenNeighboring (parent.closestMembers , problem);

            insertion(parent,childMember,population,i);

        }

    }

    private static void insertion(Member parent, Member child, Population population,int indexOf){
        calculateSolutions(parent,child);
        if (parent.solution > child.solution)
        {
            replaceMember(parent,child,population,indexOf);
            addToMOEADNonDominatedPopulation(child);
        }

        insertOnNeighborhood(parent,child);

    }



    private static void insertOnNeighborhood(Member parent, Member child) {
        for (int i = 0; i < parent.closestMembers.size(); i++)
        {
            Member neighborhoodMember = parent.closestMembers.get(i);

            calculateSolutions(neighborhoodMember,child);
            if (neighborhoodMember.solution > child.solution)
            {
                replaceMember(parent.closestMembers, i, child);
                addToMOEADNonDominatedPopulation(child);

            }

        }

    }

    private static void replaceMember(Member parent, Member child, Population population,int indexOf) {

        child.closestMembers = parent.closestMembers;
        population.population.remove(indexOf);
        population.population.add(0,child.deepCopy());

    }

    private static void replaceMember(ArrayList<Member> closestMembers, int indexOfNeighborhoodMember, Member child) {
        closestMembers.remove(indexOfNeighborhoodMember);
        closestMembers.add(child);
    }

    private static void calculateSolutions(Member parent, Member child){
        child.weightVector = parent.weightVector;
        SolutionWeightedSum.calculateSolution(child);

    }

    private static void addToMOEADNonDominatedPopulation(Member member){
        MOEAD.nonDominatedPopulation.addMember(member.deepCopy());
    }


}
