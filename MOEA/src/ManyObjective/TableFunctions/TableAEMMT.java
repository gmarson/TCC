package ManyObjective.TableFunctions;

import Constants.*;
import ManyObjective.*;
import Population.*;
import Problems.*;
import Selections.SelectionRankWeightedAverage;
import Selections.SelectionTables;
import Utilities.*;
import WeightedAverage.*;

import java.util.ArrayList;

/**
 * Created by gabrielm on 30/03/17.
 */
public class TableAEMMT extends TableFunctions{

    public static int genCounter=1;
    public ArrayList<Table> tables = new ArrayList<>();

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
        boolean shouldIncreaseContribution = false;
        ArrayList<Integer> positionsToIncrease = new ArrayList<>();
        int tablePosition = 0;
        problem.applyFunctions(newMember);

        for (Table table : tables)
        {

            if (table.isNonDominatedTable)
                shouldIncreaseContribution = insertionForNonDominatedTable(table, newMember, problem);

            else
                shouldIncreaseContribution = insertionForWeightedAverageTable(table,newMember);


            if (table.mask.equals(newMember.parentTableMask1) || table.mask.equals(newMember.parentTableMask2))
                positionsToIncrease.add(tablePosition);

            tablePosition++;

        }

        if (shouldIncreaseContribution) increaseContribution(positionsToIncrease);
    }

    @Override
    public void mainLoop(Problem problem) {
        while(genCounter < Constants.NUMBER_OF_GENERATIONS) {

            //System.out.println("Generation "+genCounter);
            if (genCounter % 50 ==0) TableFunctions.resetContributionAndConvergence(this);

            SelectionTables selectionTables = new SelectionTables();

            ArrayList<Table> parentTables = selectionTables.selectTables(tables,"AEMMT");
            Population parentsPopulation = SelectionRankWeightedAverage.selectParents(parentTables.get(0),parentTables.get(1));
            Population children = problem.crossover.crossoverAndMutation(parentsPopulation);
            super.copyMaskToChildren(parentsPopulation, children);
            this.insertMemberOnTables(children.population.get(0), problem);
            genCounter++;

        }

    }


    private boolean insertionForWeightedAverageTable(Table table, Member newMember) {

        Member worstMemberOfTable = table.pop.population.get(Constants.TABLE_SIZE-1);
        if (worstMemberOfTable.weightedAverage > newMember.weightedAverage){
            table.pop.population.remove(Constants.TABLE_SIZE-1);
            Member newMemberWithWeightedAverage = WeightedAverage.calculateWeightedAverageForSingleMember(newMember.deepCopy(),table.mask);
            Utils.insertMemberOnCrescentOrderedArrayByWeightedAverage(newMemberWithWeightedAverage,table.pop.population);

            return true;
        }

        return false;
    }


    private boolean insertionForNonDominatedTable(Table table, Member newMember, Problem problem) {
        boolean shouldIncreaseContribution = false;

        table.pop.addMember(newMember);
        table.pop.fastNonDominatedSort();

        if (Problem.instanceOfMemberIsPresent(table.pop.fronts.allFronts.get(0),newMember)){
            shouldIncreaseContribution = true;
        }

        Problem.removeSimilar(table.pop.fronts.allFronts.get(0),problem);
        table.pop.population = table.pop.fronts.allFronts.get(0).membersAtThisFront;


        return shouldIncreaseContribution;
    }

    private void increaseContribution(ArrayList<Integer> positionsToIncrease)
    {
        if(positionsToIncrease.size() == 1) positionsToIncrease.add(positionsToIncrease.get(0));
        tables.get(positionsToIncrease.get(0)).contribution++;
        tables.get(positionsToIncrease.get(1)).contribution++;

    }

    @Override
    public void reset(){
        super.reset();
        genCounter = 1;
        tables = new ArrayList<>();
    }


    @Override
    public void addTable(ArrayList<Integer> mask){
        tables.add(new Table(mask));
    }

    @Override
    public ArrayList<Table> getTables() {
        return this.tables;
    }



}
