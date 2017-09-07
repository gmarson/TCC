package Dominance;


import Population.*;
import java.util.ArrayList;
/**
 * Created by gabrielm on 09/01/17.
 */
public class Dominance {

    public void establishDominanceForAllMembers(Population p)
    {
        Member mi,mj;

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

    public void establishDominanceForAllMembers(Population p, int[] mask) {
        Member mi,mj;

        for(int i =0; i< p.population.size();i++)
        {
            mi = p.population.get(i);
            for(int j=i+1; j< p.population.size(); j++)
            {
                mj = p.population.get(j);
                establishDominanceForCoupleOfMembers(mi,mj,mask);
            }
        }

    }

    private void establishDominanceForCoupleOfMembers(Member mi, Member mj){

        if(dominates(mi,mj))
        {
            mj.numberOfSolutionsThatDominatesThisMember++;
            mi.solutionsThatThisMemberDominates.add(mj);
        }
        else if(dominates(mj,mi))
        {
            mi.numberOfSolutionsThatDominatesThisMember++;
            mj.solutionsThatThisMemberDominates.add(mi);
        }
    }

    private void establishDominanceForCoupleOfMembers(Member mi, Member mj, int[] mask){

        if(dominates(mi,mj,mask))
        {
            mj.numberOfSolutionsThatDominatesThisMember++;
            mi.solutionsThatThisMemberDominates.add(mj);
        }
        else if(dominates(mj,mi,mask))
        {
            mi.numberOfSolutionsThatDominatesThisMember++;
            mj.solutionsThatThisMemberDominates.add(mi);
        }
    }

    public boolean dominates(Member m1, Member m2)
    {
        if (m1.resultOfFunctions.size() != m2.resultOfFunctions.size()) {
            System.out.println("Deu treta! em Dominance");
            System.out.println("Deu treta! em Dominance");
            System.out.println("m1 = "+m1.resultOfFunctions);
            System.out.println("m2 = "+m2.resultOfFunctions);
        }

        boolean better = false;

        for (int i = 0; i < m1.resultOfFunctions.size() ; i++) {

            if(m1.resultOfFunctions.get(i) <= m2.resultOfFunctions.get(i))
                better = m1.resultOfFunctions.get(i) < m2.resultOfFunctions.get(i);
            else
                return false;

        }

        return better;
    }

    public boolean dominates(Member m1, Member m2, int[] mask)
    {
        if (m1.resultOfFunctions.size() != m2.resultOfFunctions.size()){
            System.out.println("Deu treta! em Dominance");
            System.out.println("m1 = "+m1.resultOfFunctions);
            System.out.println("m2 = "+m2.resultOfFunctions);
        }

        boolean better = false;

        for (int i = 0; i < mask.length ; i++) {

            if(m1.resultOfFunctions.get(mask[i]-1) <= m2.resultOfFunctions.get(mask[i]-1))
                better = m1.resultOfFunctions.get(mask[i]-1) < m2.resultOfFunctions.get(mask[i]-1);
            else
                return false;
        }

        return better;
    }
}
