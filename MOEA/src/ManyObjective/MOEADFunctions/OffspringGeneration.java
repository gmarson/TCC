package ManyObjective.MOEADFunctions;

import Dominance.Dominance;
import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighboring;
import Utilities.Utils;

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

            addToNonDominatedPopulation(child.deepCopy(), problem);

            addToNeighborhood(cell,child);

        }
    }

    private static void addToNeighborhood(Member cell, Member child) {

        for(Member neighborhoodMember : cell.closestMembers){
            SolutionWeightedSum.calculateSolution(child,neighborhoodMember.weightVector.vector);
            if(child.solution <= neighborhoodMember.solution){
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

    private static void addToNonDominatedPopulation(Member member, Problem problem){

        Dominance dominance = new Dominance();
        ArrayList<Member> toBeRemoved = new ArrayList<>();
        boolean shouldAddNewMember = true;

        if (Problem.valueOfMemberIsPresent(member,MOEAD.nonDominatedPopulation,problem)) return;

        for (Member paretoMember : MOEAD.nonDominatedPopulation.population){

            if (dominance.dominates(member,paretoMember)){
                toBeRemoved.add(paretoMember);
            }

            if (dominance.dominates(paretoMember,member)){
                shouldAddNewMember = false;
            }
        }

        MOEAD.nonDominatedPopulation.population.removeAll(toBeRemoved);
        if (shouldAddNewMember) MOEAD.nonDominatedPopulation.addMember(member);
    }

}
