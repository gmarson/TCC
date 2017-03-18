package ManyObjective;

import Constants.*;
import Population.*;
import java.util.ArrayList;

/**
 * Created by gabrielm on 11/03/17.
 */
public class Table {

    public Population pop = new Population();
    public int contribution = 0;
    public ArrayList<Integer> mask;
    public boolean isNonDominatedTable;


    public Table(ArrayList<Integer> mask){
        this.mask = mask;
        this.isNonDominatedTable = mask.size() == 0;
    }



    public void setBestMembersByRank(Population testPopulation) {
        int firstRankOfMember = testPopulation.population.get(0).rank;
        while(pop.population.size() < Constants.TABLE_SIZE && testPopulation.population.size() > 0)
        {
            if(testPopulation.population.get(0).rank > firstRankOfMember) break;
            pop.population.add(testPopulation.population.get(0).deepCopy());
            testPopulation.population.remove(0);
        }

    }

    public void setBestMembersByWeightedAverage(Population testPopulation){
        while(pop.population.size() < Constants.TABLE_SIZE){
            pop.population.add(testPopulation.population.get(0));
            testPopulation.population.remove(0);
        }
    }
}
