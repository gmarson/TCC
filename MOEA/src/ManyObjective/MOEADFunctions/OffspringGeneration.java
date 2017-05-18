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


        for (int i = 0; i < parent.closestMembers.size(); i++) {

            Member neighboringMember = parent.closestMembers.get(i);


            if (shouldReplaceMember(childMember,neighboringMember, parent.weightVector))
            {

                parent.closestMembers.set(i,childMember);
                MOEAD.nonDominatedPopulation.addMember(childMember);
            }
        }
    }


    private static boolean shouldReplaceMember(Member childMember, Member neighboringMember,WeightVector parentWeightVector){


        childMember.weightVector = parentWeightVector;
        neighboringMember.weightVector = parentWeightVector;
        SolutionWeightedSum.calculateSolution(childMember);
        SolutionWeightedSum.calculateSolution(neighboringMember);

        return childMember.solution < neighboringMember.solution;
    }

}
