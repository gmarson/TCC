package ManyObjective;

import Constants.*;
import Dominance.Dominance;
import Population.*;
import WeightedAverage.WeightedAverage;

import java.util.ArrayList;

/**
 * Created by gabrielm on 11/03/17.
 */

public class Table {

    public Population tablePopulation = new Population();
    public int contribution = 0;
    public int convergence = 0;
    public int[] mask;
    public boolean isNonDominatedTable;
    private Dominance dominance = new Dominance();

    public Table(int[] mask){
        this.mask = mask;

        this.isNonDominatedTable = (mask.length == 0);
    }

    public void organizeNonDominatedTable(boolean removeSurplusMembers)
    {
        this.resetDominanceStatus();
        dominance.establishDominanceForAllMembers(this.tablePopulation);
        Population pop = new Population();
        ArrayList<Member> members = this.tablePopulation.population;

        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            if (member.numberOfSolutionsThatDominatesThisMember == 0){
                pop.addMember(member.deepCopy());
            }
        }

        tablePopulation = pop.deepCopy();
        if (removeSurplusMembers) removeSurplusMembers();
    }

    public void organizeWeightedAverageTable(){
        WeightedAverage.sortByWeightedAverage(this.tablePopulation);
        removeSurplusMembers();
    }

    private void removeSurplusMembers(){
        if (tablePopulation.population.size() > Constants.TABLE_SIZE){
            this.tablePopulation.population = new ArrayList<Member>(tablePopulation.population.subList(0,Constants.TABLE_SIZE));
        }
    }

    public void resetContributionAndConvergence(){
        this.contribution = 0;
        this.convergence = 0;
    }

    private void resetDominanceStatus(){
        for(Member member: tablePopulation.population){
            member.numberOfSolutionsThatDominatesThisMember = 0;
            member.solutionsThatThisMemberDominates = new ArrayList<>();
        }
    }

    public void organizeNonDominatedMaskedTable(boolean removeSurplusMembers)
    {
        this.resetDominanceStatus();
        dominance.establishDominanceForAllMembers(this.tablePopulation, this.mask);
        Population pop = new Population();
        ArrayList<Member> members = this.tablePopulation.population;

        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            if (member.numberOfSolutionsThatDominatesThisMember == 0){
                pop.addMember(member.deepCopy());
            }
        }

        tablePopulation = pop;
        if (removeSurplusMembers) removeSurplusMembers();
    }

}
