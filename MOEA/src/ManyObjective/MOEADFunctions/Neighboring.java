package ManyObjective.MOEADFunctions;

import Constants.Constants;
import Population.*;
import Utilities.Utils;
import com.sun.xml.internal.ws.api.model.MEP;

import java.util.ArrayList;
import java.util.Arrays;

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
                tryToAdd(parentMember, childMember.deepCopy());
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
          m.weightVector = new WeightVector();

        }
    }

    public static void tryToAdd(Member parentMember, Member memberToBeInserted){
        memberToBeInserted.closestMembers = new ArrayList<Member>();
        ArrayList<Member> closestMembersFromParent = parentMember.closestMembers;
        if (closestMembersFromParent.isEmpty()) {
            closestMembersFromParent.add(memberToBeInserted);
        }
        else
        {
            for (int i = 0; i < closestMembersFromParent.size(); i++) {
                if(closestMembersFromParent.get(i).distanceFromParentMember > memberToBeInserted.distanceFromParentMember)
                {
                    addIfNear(parentMember,memberToBeInserted,i);
                    break;
                }
            }

            if(closestMembersFromParent.size() < Constants.NEIGHBOUR_QTD)
                parentMember.closestMembers.add(memberToBeInserted);

        }
    }


    private static void addIfNear(Member parentMember, Member memberToBeInserted, int index){
        if (parentMember.closestMembers.size() == Constants.NEIGHBOUR_QTD)
        {
            int last = parentMember.closestMembers.size()-1;
            parentMember.closestMembers.remove(last);
            parentMember.closestMembers.add(memberToBeInserted);
        }
        else
        {
            parentMember.closestMembers.add(index,memberToBeInserted);
        }
    }


    public static boolean shouldReplace(Member parentMember, Member opponentMember){


        opponentMember.weightVector = parentMember.weightVector;
        SolutionWeightedSum.calculateSolution(opponentMember);

        return opponentMember.solution < parentMember.solution;
    }






}
