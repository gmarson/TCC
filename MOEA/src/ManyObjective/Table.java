package ManyObjective;

import Constants.*;
import Population.*;
import java.util.ArrayList;
import Utilities.*;

/**
 * Created by gabrielm on 11/03/17.
 */
public class Table {

    public Population pop = new Population();
    public int contribution = 0;
    public int convergence = 0;
    public ArrayList<Integer> mask;
    public boolean isNonDominatedTable;


    public Table(ArrayList<Integer> mask){
        this.mask = mask;
        this.isNonDominatedTable = mask.size() == 0;
    }


    public void setBestMembersByRank(Population testPopulation) {

        Member m;

        while(pop.population.size() < Constants.TABLE_SIZE && testPopulation.population.size() > 0)
        {

            m = testPopulation.population.get(0).deepCopy();

            if (mask.size() ==1) applyWeightedAverageInSingleObjectiveMember(m);

            pop.population.add(m);
            testPopulation.population.remove(0);

        }


    }

    public void setBestMembersByWeightedAverage(Population testPopulation){
        while(pop.population.size() < Constants.TABLE_SIZE){
            pop.population.add(testPopulation.population.get(0));
            testPopulation.population.remove(0);
        }
    }

    public void resetContributionAndConvergence(){
        this.contribution = 0;
        this.convergence = 0;
    }

    private void applyWeightedAverageInSingleObjectiveMember(Member member){

        member.weightedAverage = member.resultOfFunctions.get(mask.get(0)-1);
    }




}
