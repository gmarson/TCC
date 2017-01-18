import java.util.ArrayList;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class Population {

    protected ArrayList<Member> population = new ArrayList<>();
    protected Problem problem;
    protected Fronts fronts = new Fronts();
    protected static Dominance dominance = new Dominance();

    public Population()
    {
        this.problem = new ProblemSCH();
    }

    public void fastNonDominatedSort()
    {
        resetAttributesAndFrontsForAllMembers();
        dominance.establishDominanceForAllMembers(this);
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

            //SPEA2
            member.strength = 0;
            member.density = 0;
            member.rawFitness = 0;
            member.fitness = 0;
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

    public void mergeTwoPopulations(Population currentPopulation, Population archive)
    {
        ArrayList<Member> p1 = new ArrayList<>(currentPopulation.population);
        ArrayList<Member> p2 = new ArrayList<>(archive.population);
        this.population.addAll(p1);
        this.population.addAll(p2);
    }

    public void addFrontToPopulation(Front front){
        this.fronts.allFronts.add(front);
        for(Member member: front.membersAtThisFront)
        {
            this.population.add(member);
        }

    }

    public Front getNonDominatedFront()
    {
        return this.fronts.allFronts.get(0);
    }




}
