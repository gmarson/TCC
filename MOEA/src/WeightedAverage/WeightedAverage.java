package WeightedAverage;

import Constants.*;
import Population.*;
import ManyObjective.*;
import Utilities.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 18/03/17.
 */
public class WeightedAverage {

    public void setWeightedAverageForAllMembers(Population population)
    {
        Member member;

        for (int i = 0; i < population.population.size(); i++) {
            member = population.population.get(i);
            setWeightedAverage(member);
        }

    }

    public void establishWeightedAverageRelationsForTable(Population population){
        setWeightedAverageForAllMembers(population);

        ArrayList<Member> orderedArray = new ArrayList<>();
        for(Member member:population.population){
            Utils.insertMemberOnCrescentOrderedArrayByWeightedAverage(member,orderedArray);
        }

        population.population = new ArrayList<Member>(orderedArray);

    }

    private void setWeightedAverage(Member member)
    {
        double weightedSum= 0.0;

        for (int i = 0; i < member.resultOfFunctions.size() ; i++) {
            weightedSum += member.resultOfFunctions.get(i);

        }
        member.weightedAverage = weightedSum/member.resultOfFunctions.size();

    }

    public static Member calculateWeightedAverageForSingleMember(Member member){
        double weightedSum=0.0;
        for (int i = 0; i < member.resultOfFunctions.size(); i++) {

            weightedSum += member.resultOfFunctions.get(i);
        }
        member.weightedAverage = weightedSum/member.resultOfFunctions.size();
        return member;
    }


}
