package Dominance;

import Constants.*;
import ManyObjective.Table;
import Population.*;
import java.util.ArrayList;
/**
 * Created by gabrielm on 09/01/17.
 */
public class Dominance {

    public static boolean haveToUseMask = false;
    public static ArrayList<Integer> currentMask;

    public void establishDominanceForAllMembers(Population p, ArrayList<Integer> maskOfObjectives)
    {
        Member mi,mj;
        haveToUseMask = !(maskOfObjectives == null || maskOfObjectives.isEmpty());
        currentMask = haveToUseMask? maskOfObjectives: null;

        for(int i =0; i< p.population.size();i++)
        {
            mi = p.population.get(i);
            for(int j=i+1; j< p.population.size(); j++)
            {
                mj = p.population.get(j);
                establishDominanceForCoupleOfMembers(mi,mj);

            }
        }
    }


    private void establishDominanceForCoupleOfMembers(Member mi, Member mj){

        if(haveToUseMask? dominatesWithMask(mi,mj) : dominates(mi,mj))
        {
            mj.numberOfSolutionsThatDominatesThisMember++;
            mi.solutionsThatThisMemberDominates.add(mj);
        }
        else if(haveToUseMask? dominatesWithMask(mj,mi) : dominates(mj,mi))
        {
            mi.numberOfSolutionsThatDominatesThisMember++;
            mj.solutionsThatThisMemberDominates.add(mi);
        }
    }

    public boolean dominates(Member m1, Member m2)
    {
        boolean better = false;

        for (int i = 0; i < Constants.PROBLEM_SIZE ; i++) {

            if(m1.resultOfFunctions.get(i) <= m2.resultOfFunctions.get(i))
                better = m1.resultOfFunctions.get(i) < m2.resultOfFunctions.get(i);
            else
                return false;

        }

        return better;
    }


    //will also work for AEMMD
    public boolean dominatesWithMask(Member m1, Member m2){
        boolean better = false;
        int currentPosition;

        for (int i = 0; i < currentMask.size() ; i++) {
            currentPosition = currentMask.get(i)-1;

            if(m1.resultOfFunctions.get(currentPosition) <= m2.resultOfFunctions.get(currentPosition))
                better = m1.resultOfFunctions.get(currentPosition) < m2.resultOfFunctions.get(currentPosition);
            else
                return false;

        }

        return better;
    }





}
