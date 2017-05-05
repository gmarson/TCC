package ManyObjective.MOEADFunctions;

import Population.*;
/**
 * Created by gabrielm on 30/04/17.
 */
public class Solution {

    public static void setSolution(Member member){
        member.solution =0.0;
        for (int i = 0; i < member.resultOfFunctions.size(); i++) {
            member.solution += (member.resultOfFunctions.get(i) * member.weightVector[i]);
        }
    }


    public static void setSolutionForPopulation(Population population){
        for (Member member : population.population){
            setSolution(member);
        }
    }

}
