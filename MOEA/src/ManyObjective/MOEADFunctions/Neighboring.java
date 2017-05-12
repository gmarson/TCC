package ManyObjective.MOEADFunctions;

import Constants.Constants;
import Population.Member;
import Population.Population;
import Utilities.Utils;

import java.util.Scanner;

/**
 * Created by gabrielm on 30/04/17.
 */
public class Neighboring {


    public static void setClosestNeighbours(Member parentMember, Population population){


        for(Member childMember: population.population)
        {
            if(!parentMember.equals(childMember))
            {
                childMember.distanceFromParentMember = Utils.euclidianDistanceBasedOnDistanceVector(parentMember,childMember);
                addIfNear(parentMember, childMember);
            }

            childMember.distanceFromParentMember = Constants.DEFAULT_DISTANCE_VALUE;
        }
    }

    public static void setNeighboursOfAllMembers(Population population){
        for (Member parentMember: population.population)
        {
            setClosestNeighbours(parentMember,population);
        }


    }



    public static void createWeightVectorForPopulation(Population population) {
        for (Member m : population.population) {
          m.weightVector = Utils.randSum(Constants.PROBLEM_SIZE, 1.0);

        }
    }

    public static void addIfNear(Member parentMember, Member memberToBeInserted){
        if (parentMember.closestMembers.isEmpty())
            parentMember.closestMembers.add(memberToBeInserted.deepCopy());
        else
        {
            int i=0;
            while(memberToBeInserted.distanceFromParentMember > parentMember.closestMembers.get(i).distanceFromParentMember)
            {
                i++;
                if(i == Constants.NEIGHBOUR_QTD ) return;
                if(i == parentMember.closestMembers.size()) break;
            }

            regulatedAddition(parentMember,memberToBeInserted,i);
        }
    }

    private static void regulatedAddition(Member parentMember, Member memberToBeInserted, int index){

        parentMember.closestMembers.add(index,memberToBeInserted.deepCopy());
        if( parentMember.closestMembers.size() > Constants.NEIGHBOUR_QTD){
            int indexToBeRemoved = parentMember.closestMembers.size()-1;
            parentMember.closestMembers.remove(indexToBeRemoved);
        }
    }


    public static boolean shouldReplace(Member parentMember, Member opponentMember){


        opponentMember.weightVector = parentMember.weightVector;
        SolutionWeightedSum.calculateSolution(opponentMember);

        return opponentMember.solution < parentMember.solution;
    }

}
