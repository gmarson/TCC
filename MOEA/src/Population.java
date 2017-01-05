import java.util.ArrayList;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class Population {

    protected ArrayList<Member> population = new ArrayList<>();
    protected Problem problem;
    protected Fronts fronts = new Fronts();


    public Population()
    {
        this.problem = new ProblemSCH();
    }

    public void fastNonDominatedSort()
    {
        resetAttributesAndFrontsForAllMembers();
        establishDominanceForAllMembers();
        this.population =sortPopulationByDominance();

        this.fronts.buildOrderedFronts(this);

    }

    public void resetAttributesAndFrontsForAllMembers()
    {
        this.fronts = new Fronts();
        for(Member member:population)
        {
            member.numberOfSolutionsThatDominatesThisMember =0;
            member.solutionsThatThisMemberDominates = new ArrayList<>();
            member.rank = -1;
            member.crowdingDistanceValue = 0;
        }

    }

    public ArrayList<Member> sortPopulationByDominance()
    {
        ArrayList<Member> sortedPopulation = new ArrayList<Member>();
        int currentRank = 0;

        while(stopConditionSort() == false)
        {
            for(Member member: this.population)
            {
                checkConditionAndInsertToFront(member, sortedPopulation, currentRank);
                member.numberOfSolutionsThatDominatesThisMember--;
            }

            if(shouldBuildRank())
                currentRank++;
        }

        return  sortedPopulation;
    }


    private boolean shouldBuildRank()
    {
        for(Member member: this.population)
        {
            if(member.numberOfSolutionsThatDominatesThisMember == 0)
                return true;

        }
        return false;
    }

    private void checkConditionAndInsertToFront(Member member, ArrayList<Member> sortedPopulation, int currentRank)
    {
        if(member.numberOfSolutionsThatDominatesThisMember == 0){
            sortedPopulation.add(member);
            member.rank = currentRank;
        }

    }

    private  boolean stopConditionSort()
    {
        for (Member member: this.population)
        {
            if(member.numberOfSolutionsThatDominatesThisMember >= 0)
            {
                return false;
            }
        }
        return true;
    }

    public void establishDominanceForAllMembers()
    {
        Member mi,mj;
        for(int i =0; i< this.population.size();i++)
        {
            mi = this.population.get(i);
            for(int j=i+1; j< this.population.size(); j++)
            {
                mj = this.population.get(j);

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

        for (int i = 0; i < Problem.PROBLEM_SIZE ; i++) {

            if(m1.resultOfFunctions.get(i) <= m2.resultOfFunctions.get(i))
            {

                if(m1.resultOfFunctions.get(i) < m2.resultOfFunctions.get(i))
                {
                    better = true;
                }
            }
            else
            {
                return false;
            }
        }
        if(better)
            return true;

        return false;

    }

    public void addMember(Member member)
    {
        this.population.add(member);
    }

    public Population mergeWithCurrentPopulation(Population another)
    {
        Population mergedPopulation = new Population();
        mergedPopulation.population = new ArrayList<>(this.population);
        mergedPopulation.population.addAll(another.population);
        return mergedPopulation;
    }

    public void addFrontToPopulation(Front front){
        this.fronts.allFronts.add(front);
        for(Member member: front.membersAtThisFront)
        {
            this.population.add(member);
        }

    }


}
