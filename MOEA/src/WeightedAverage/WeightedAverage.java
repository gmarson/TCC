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


    public ArrayList<Integer> currentMask;


    public void setWeightedAverageForAllMembers(Population population, ArrayList<Integer> mask)
    {
        Member member;
        currentMask = mask;

        for (int i = 0; i < population.population.size(); i++) {
            member = population.population.get(i);
            setWeightedAverage(member);
        }

    }

    public void establishWeightedAverageRelationsForTable(Population population,ArrayList<Integer> mask){
        setWeightedAverageForAllMembers(population,mask);

        ArrayList<Member> orderedArray = new ArrayList<>();
        for(Member member:population.population){
            Utils.insertMemberOnCrescentOrderedArrayByWeightedAverage(member,orderedArray);
        }

        population.population = new ArrayList<Member>(orderedArray);

    }



    public void setWeightedAverage(Member member)
    {
        double weightedSum= 0.0;

        for (int i = 0; i < this.currentMask.size() ; i++) {
            int position = this.currentMask.get(i)-1;
            weightedSum += member.resultOfFunctions.get(position);

        }
        member.weightedAverage = weightedSum/this.currentMask.size();

    }


}
