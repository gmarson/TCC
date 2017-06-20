package ManyObjective;

import Constants.*;
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


    public void setBestMembersForSingleObjectiveTables(){
        while (tablePopulation.population.size() > Constants.TABLE_SIZE)
        {
            int size = tablePopulation.population.size()-1;
            tablePopulation.population.remove(size);
        }
    }

    public void setBestMembersForNonDominatedTable() {

        while(tablePopulation.population.size() > Constants.TABLE_SIZE)
        {
            int size = tablePopulation.population.size()-1;
            tablePopulation.population.remove(size);
        }

        if (mask.length ==1) applyWeightedAverageForPopulation();

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

    private void applyWeightedAverageForPopulation(){
        for (Member m : tablePopulation.population){
            applyWeightedAverageInSingleObjectiveMember(m);
        }
    }

    private void applyWeightedAverageInSingleObjectiveMember(Member member){

        member.weightedAverage = member.resultOfFunctions.get(0);
    }




}
