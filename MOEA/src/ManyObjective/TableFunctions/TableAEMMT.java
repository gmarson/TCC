package ManyObjective.TableFunctions;

import Constants.*;
import ManyObjective.*;
import Population.*;
import Problems.*;
import Utilities.*;
import WeightedAverage.*;

import java.util.ArrayList;

/**
 * Created by gabrielm on 30/03/17.
 */
public class TableAEMMT extends TableFunctions{


    @Override
    public void fillTables(){
        parentPopulation.fastNonDominatedSort();

        for(Table table: tables)
        {
            Population testPopulation = new Population(parentPopulation);
            if (table.mask.size() <=1)
            {
                testPopulation.fastNonDominatedSort(table.mask);
                if (table.mask.size() == 1)
                    table.setBestMembersByRank(testPopulation);
                else
                    table.setBestMembersByRank(new Population(testPopulation.fronts.allFronts.get(0)));
            }
            else
            {
                Population.weightedAverage.establishWeightedAverageRelationsForTable(testPopulation,table.mask);
                table.setBestMembersByWeightedAverage(testPopulation);
            }

        }

    }

    @Override
    public void insertMemberOnTables(Member newMember, Problem problem) {
        boolean shouldIncrease = false;
        ArrayList<Integer> positionsToIncrease = new ArrayList<>();
        int tablePosition = 0;
        problem.applyFunctions(newMember);

        for (Table table : tables)
        {

            if (table.isNonDominatedTable)
                shouldIncrease = insertionForNonDominatedTable(table, newMember, problem);

            else
                shouldIncrease = insertionForWeightedAverageTable(table,newMember);


            if (table.mask.equals(newMember.parentTableMask1) || table.mask.equals(newMember.parentTableMask2))
                positionsToIncrease.add(tablePosition);

            tablePosition++;

        }

        if (shouldIncrease) increaseContribution(positionsToIncrease);
    }

    boolean insertionForWeightedAverageTable(Table table, Member newMember) {

        Member worstMemberOfTable = table.pop.population.get(Constants.TABLE_SIZE-1);
        if (worstMemberOfTable.weightedAverage > newMember.weightedAverage){
            table.pop.population.remove(Constants.TABLE_SIZE-1);
            Member newMemberWithWeightedAverage = WeightedAverage.calculateWeightedAverageForSingleMember(newMember.deepCopy(),table.mask);
            Utils.insertMemberOnCrescentOrderedArrayByWeightedAverage(newMemberWithWeightedAverage,table.pop.population);

            return true;
        }

        return false;
    }


    boolean insertionForNonDominatedTable(Table table, Member newMember, Problem problem) {
        table.pop.addMember(newMember);
        table.pop.fastNonDominatedSort();

        if (Problem.memberIsPresent(table.pop.fronts.allFronts.get(0),newMember)){
            return true;
        }

        Problem.removeSimilar(table.pop.fronts.allFronts.get(0),problem);
        table.pop.population = table.pop.fronts.allFronts.get(0).membersAtThisFront;


        return false;
    }

    private void increaseContribution(ArrayList<Integer> positionsToIncrease)
    {
        if(positionsToIncrease.size() == 1) positionsToIncrease.add(positionsToIncrease.get(0));
        tables.get(positionsToIncrease.get(0)).contribution++;
        tables.get(positionsToIncrease.get(1)).contribution++;

    }
}
