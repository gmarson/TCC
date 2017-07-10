package ManyObjective;

import Constants.*;
import Dominance.Dominance;
import Population.*;
import Utilities.Printer;

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

    public Table(int[] mask){
        this.mask = mask;
        this.isNonDominatedTable = mask.length == 0;
    }

    public void setBestMembersByWeightedAverage(){
        while(tablePopulation.population.size() > Constants.TABLE_SIZE)
        {
            int size = tablePopulation.population.size()-1;
            tablePopulation.population.remove(size);
        }
    }

    public void resetContributionAndConvergence(){
        this.contribution = 0;
        this.convergence = 0;
    }

    public void organizeNonDominatedTable()
    {
        Dominance dominance = new Dominance();
        dominance.establishDominanceForAllMembers(this.tablePopulation);
        ArrayList<Member> members = this.tablePopulation.population;

        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            if (member.numberOfSolutionsThatDominatesThisMember != 0){
                members.remove(member);
            }
        }

        if (members.size() > Constants.TABLE_SIZE){
            this.tablePopulation.population = new ArrayList<Member>(members.subList(0,Constants.TABLE_SIZE-1));
        }

    }

}
