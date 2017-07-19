package Population;

import Dominance.*;

import java.io.Serializable;
import java.util.ArrayList;
import Fronts.*;
import WeightedAverage.*;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class Population implements Serializable, Cloneable {

    public ArrayList<Member> population = new ArrayList<>();

    public Fronts fronts = new Fronts();

    public static Dominance dominance = new Dominance();

    public Population()
    {

    }

    public Population deepCopy(){
        Population pop = new Population();
        for (Member member : population)
        {
            pop.addMember(member.deepCopy());
        }

        return  pop;
    }

    public Population(Population p)
    {
        this.fronts = p.fronts;
        this.population = p.population;
    }

    public Population(Front front)
    {
        this.population = front.membersAtThisFront;
    }

    public void fastNonDominatedSort()
    {
        resetAttributesAndFrontsForAllMembers();
        dominance.establishDominanceForAllMembers(this);
        this.population =sortPopulationByDominance();
        this.fronts.buildOrderedFronts(this);
    }

    public void removeAllButNonDominated(){
        resetDominanceStatus();
        dominance.establishDominanceForAllMembers(this);
        ArrayList<Member> aux = new ArrayList<>();

        for (Member member : population){
            if (member.numberOfSolutionsThatDominatesThisMember == 0){
                aux.add(member);
            }
        }

        population = aux;
    }

    private void resetDominanceStatus(){
        for(Member member : population){
            member.solutionsThatThisMemberDominates = new ArrayList<>();
            member.numberOfSolutionsThatDominatesThisMember = 0;
        }
    }

    private void resetAttributesAndFrontsForAllMembers()
    {
        this.fronts = new Fronts();
        for(Member member:population)
        {
            //NSGAII.NSGAII
            member.numberOfSolutionsThatDominatesThisMember =0;
            member.solutionsThatThisMemberDominates = new ArrayList<>();
            member.rank = -1;
            member.crowdingDistanceValue = 0;

            //SPEA2.SPEA2
            member.strength = 0;
            member.density = 0;
            member.rawFitness = 0;
            member.fitness = 0;
            member.distances = new ArrayList<>();

        }

    }

    private ArrayList<Member> sortPopulationByDominance()
    {
        ArrayList<Member> sortedPopulation = new ArrayList<>();
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
        this.population = new ArrayList<>(); 
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

    public Population getNonDominatedSPEA2()
    {
        Population newArchive = new Population();
        for(Member member: this.population){
            if(member.fitness < 1.0)
                newArchive.population.add(member.deepCopy());
        }

        return newArchive;
    }

    public Front getFirstFront(){
        return this.fronts.allFronts.get(0);
    }

    public void clearObjectiveFunctions() {
        for(Member member: this.population)
        {
            member.resultOfFunctions = new ArrayList<>();
        }
    }
}
