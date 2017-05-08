package ManyObjective.MOEADFunctions;

import Constants.Constants;
import Population.Member;
import Population.Population;
import Utilities.Printer;
import Utilities.Utils;

import java.util.Scanner;

/**
 * Created by gabrielm on 30/04/17.
 */
public class Neighboring {

    //determinar os vizinhos de cada indivíduo. fazer a funcao para um individuo de cada vez

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
        //todo diminuir comparacoes
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
        if (parentMember.distanceFromClosestMembers.isEmpty())
            parentMember.distanceFromClosestMembers.add(memberToBeInserted.deepCopy());
        else
        {
            int i=0;
            while(memberToBeInserted.distanceFromParentMember > parentMember.distanceFromClosestMembers.get(i).distanceFromParentMember)
            {
                i++;
                if(i == Constants.NEIGHBOUR_QTD ) return;
                if(i == parentMember.distanceFromClosestMembers.size()) break;
            }

            regulatedAddition(parentMember,memberToBeInserted,i);
        }
    }

    private static void regulatedAddition(Member parentMember, Member memberToBeInserted, int index){

        parentMember.distanceFromClosestMembers.add(index,memberToBeInserted.deepCopy());
        if( parentMember.distanceFromClosestMembers.size() > Constants.NEIGHBOUR_QTD){
            int indexToBeRemoved = parentMember.distanceFromClosestMembers.size()-1;
            parentMember.distanceFromClosestMembers.remove(indexToBeRemoved);
        }
    }


    public static boolean shouldReplace(Member parentMember, Member opponentMember){


        opponentMember.weightVector = parentMember.weightVector;
        SolutionWeightedSum.calculateSolution(opponentMember);

        return opponentMember.solution < parentMember.solution;
    }

}
