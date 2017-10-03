package WeightedAverage;

import Population.*;
import SupportingFiles.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 18/03/17.
 */
public class WeightedAverage {

    private static void calculateWeightedAverageForAllMembers(Population population)
    {
        Member member;

        for (int i = 0; i < population.population.size(); i++) {
            member = population.population.get(i);
            calculateWeightedAverage(member);
        }

    }

    public static void sortByWeightedAverage(Population population){
        calculateWeightedAverageForAllMembers(population);

        ArrayList<Member> orderedArray = new ArrayList<Member>();
        for(Member member:population.population){
            Utils.insertMemberOnCrescentOrderedArrayByWeightedAverage(member,orderedArray);
        }

        population.population = new ArrayList<Member>(orderedArray);

    }

    public static void calculateWeightedAverage(Member member)
    {
        double weightedSum= 0.0;

        for (int i = 0; i < member.resultOfFunctions.size() ; i++) {
            weightedSum += member.resultOfFunctions.get(i);

        }
        member.weightedAverage = weightedSum/member.resultOfFunctions.size();

    }
}
