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

                /*mi.printMemberResultOfFunctions(); // todo
                mj.printMemberResultOfFunctions(); // todo
                System.out.println("o Primeiro dominou o segundo"+ dominates(mi,mj));//todo
                System.out.println("O segundo dominou o primeiro"+dominates(mj,mi));//todo
                System.out.println();
                */

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


}
