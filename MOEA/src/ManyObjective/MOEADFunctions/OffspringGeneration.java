package ManyObjective.MOEADFunctions;

import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighboring;

import java.util.ArrayList;
import java.util.Arrays;


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

        for (Member parent : population.population)
        {
            Member childMember = generateChildGivenNeighboring (parent.closestMembers , problem);

            tryAddingChildToNeighboring(parent,childMember);

        }

    }

    private static void tryAddingChildToNeighboring(Member parent, Member childMember){

        if (shouldReplaceMember(childMember,parent))
        {
            parent.solution = childMember.solution;
            parent.value = childMember.value;
            parent.binaryValue = childMember.binaryValue;
            MOEAD.nonDominatedPopulation.addMember(childMember);


        }
        // adicionei esse trecho acimca

        for (int i = 0; i < parent.closestMembers.size(); i++) {

            Member neighboringMember = parent.closestMembers.get(i);

            if (shouldReplaceMember(childMember,neighboringMember))
            {
                neighboringMember.binaryValue = childMember.binaryValue;
                neighboringMember.value = childMember.value;
                neighboringMember.solution = childMember.solution;

                MOEAD.nonDominatedPopulation.addMember(childMember);
            }
        }

        //System.out.println("Tamanho da vizinhanca: "+parent.closestMembers.size() );//todo
    }

    private static boolean shouldReplaceMember(Member childMember, Member neighboringMember)
    {
        childMember.weightVector = neighboringMember.weightVector;

        SolutionWeightedSum.calculateSolution(childMember);
        SolutionWeightedSum.calculateSolution(neighboringMember);

        return childMember.solution < neighboringMember.solution;
    }

}
