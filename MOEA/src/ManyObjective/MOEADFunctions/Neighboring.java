package ManyObjective.MOEADFunctions;

import Constants.Constants;
import Population.*;
import Utilities.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 30/04/17.
 */
public class Neighboring {

    private static void setClosestNeighbours(Member cell, Population population)
    {
        cell.closestMembers = new ArrayList<>();

        for(Member childMember: population.population)
        {
            childMember.distanceFromParentMember = Utils.euclideanDistanceBasedOnWeightVector(cell,childMember);
            insertMemberByDistanceWithNeighborhoodLength(childMember.deepCopyForChildMembers(),cell.closestMembers);
        }
    }

    public static void setNeighboursForAllMembers(Population population){
        for (Member cell: population.population)
        {
            setClosestNeighbours(cell,population);
        }
    }

    public static void createWeightVectorForPopulation(Population population) {
        for (Member m : population.population) {
            m.weightVector = new WeightVector();
        }
    }

    private static void insertMemberByDistanceWithNeighborhoodLength(Member memberToBeInserted, ArrayList<Member> neighboring){

        if (neighboring.size() < Constants.NEIGHBOURHOOD_SIZE)
            neighboring.add(memberToBeInserted);
        else {
            int i;

            for (i = 0; i < neighboring.size(); i++) {
                if (memberToBeInserted.distanceFromParentMember < neighboring.get(i).distanceFromParentMember) {
                    neighboring.add(i, memberToBeInserted);
                    break;
                }
            }

            if (neighboring.size() > Constants.NEIGHBOURHOOD_SIZE) neighboring.remove(neighboring.size() - 1);
        }
    }
}
