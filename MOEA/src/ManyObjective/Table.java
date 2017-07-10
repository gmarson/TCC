package ManyObjective;

import Constants.*;
import Dominance.Dominance;
import Population.*;
import Utilities.Printer;
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
        this.isNonDominatedTable = mask.length == 0;
    }

    public void organizeNonDominatedTable()
    {
        dominance.establishDominanceForAllMembers(this.tablePopulation);
        ArrayList<Member> members = this.tablePopulation.population;

        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            if (member.numberOfSolutionsThatDominatesThisMember != 0){
                members.remove(member);
            }
        }

        tablePopulation.population = members;
        removeSurplusMembers();
    }

    public void organizeWeightedAverageTable(){
        WeightedAverage.sortByWeightedAverage(this.tablePopulation);
        removeSurplusMembers();
    }

    public void removeSurplusMembers(){
        if (tablePopulation.population.size() > Constants.TABLE_SIZE){
            this.tablePopulation.population = new ArrayList<Member>(tablePopulation.population.subList(0,Constants.TABLE_SIZE));
        }
    }

    public void resetContributionAndConvergence(){
        this.contribution = 0;
        this.convergence = 0;
    }

}
