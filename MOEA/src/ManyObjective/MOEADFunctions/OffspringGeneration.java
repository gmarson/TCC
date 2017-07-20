package ManyObjective.MOEADFunctions;

import Constants.Constants;
import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighboring;
import Utilities.Utils;
import com.sun.tools.internal.jxc.ap.Const;

import java.util.ArrayList;

/**
 * Created by gabrielm on 06/05/17.
 */
public class OffspringGeneration {

    private static Member generateChildrenGivenNeighboring(ArrayList<Member> parentNeighboring, Problem problem){

        Population parentsPopulation = SelectionNeighboring.selectParents(parentNeighboring);
        Population childPopulation = problem.crossover.crossoverAndMutation(parentsPopulation);
        problem.evaluateAgainstObjectiveFunctions(childPopulation);

        return childPopulation.population.get(Utils.getRandom(0,2));
    }

    public static void updateNeighboring(Population population, Problem problem){

        for (Member cell: population.population){
            Member child = generateChildrenGivenNeighboring(cell.closestMembers , problem);


            addToNonDominatedPopulation(child.deepCopy());
            if (!Problem.valueOfMemberIsPresent(child,cell.closestMembers,problem)){

                insertion(cell,child);
            }
        }

    }

    private static void insertion(Member cell, Member child) {
        for (int i = 0; i < cell.closestMembers.size(); i++) {
            Member neighborhoodMember = cell.closestMembers.get(i);
            SolutionWeightedSum.calculateSolution(child,neighborhoodMember.weightVector.vector);

            if (neighborhoodMember.solution > child.solution)
            {
                replaceMember(neighborhoodMember,child);
            }
        }
    }

    private static void replaceMember(Member neighborhoodMember, Member child){
        neighborhoodMember.solution = child.solution;
        neighborhoodMember.resultOfFunctions = child.resultOfFunctions;
        neighborhoodMember.value = child.value;
        neighborhoodMember.binaryValue = child.binaryValue;
    }

    private static void addToNonDominatedPopulation(Member memberToBeInserted){
        memberToBeInserted.weightVector = null;
        memberToBeInserted.closestMembers = null;

        MOEAD.nonDominatedPopulation.addMember(memberToBeInserted);
    }

}
