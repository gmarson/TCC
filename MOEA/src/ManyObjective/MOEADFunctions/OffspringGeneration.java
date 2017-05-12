package ManyObjective.MOEADFunctions;

import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighboring;



/**
 * Created by gabrielm on 06/05/17.
 */
public class OffspringGeneration {

    public static Member generateChildGivenMember(Population populationWithSingleMember, Problem problem){

        SelectionNeighboring selectionNeighboring = new SelectionNeighboring();
        Population parentPopulation = selectionNeighboring.selectParents(populationWithSingleMember);
        Population childPopulation = problem.crossover.crossoverAndMutation(parentPopulation);
        problem.evaluateAgainstObjectiveFunctions(childPopulation);
        Member child = childPopulation.population.get(0);

        return child;
    }


    public static void updateNeighboring(Population population, Problem problem){
        Population populationWithSingleMember = new Population();

        for (int i = 0; i < population.population.size(); i++) {


            Member parentMember = population.population.get(i).deepCopy();
            populationWithSingleMember.addMember(parentMember);
            Member newMember = generateChildGivenMember(populationWithSingleMember, problem);

            for (int j = 0; j < parentMember.closestMembers.size(); j++) {

                Member currentMember = parentMember.closestMembers.get(j);

                if (Neighboring.shouldReplace(currentMember,newMember)) {
                    copyAttributes(currentMember, newMember);
                    MOEAD.nonDominatedPopulation.addMemberWhithouClosestMembers(currentMember.deepCopy());

                }
            }



            populationWithSingleMember = new Population();

        }





    }

    private static void copyAttributes(Member parentMember, Member child) {
        parentMember.solution = child.solution;
        parentMember.resultOfFunctions = child.resultOfFunctions;
        parentMember.binaryValue = child.binaryValue;
        parentMember.value = child.value;
    }


}
