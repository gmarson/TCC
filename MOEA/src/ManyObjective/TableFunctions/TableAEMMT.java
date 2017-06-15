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

    private static int genCounter=1;
    public static ArrayList<Table> tables = new ArrayList<>();


    @Override
    public void fillTables(Problem problem, Population p){

        for(Table table: tables)
        {


            table.tablePopulation = p.deepCopy();


            problem.evaluateAgainstMask(table.tablePopulation,table.mask);



            if (table.mask.size() <=1)
            {

                table.tablePopulation.fastNonDominatedSort();
                if (table.mask.size() == 1)
                {

                    table.setBestMembersForSingleObjectiveTables();
                }
                else
                {

                    table.tablePopulation.population = table.tablePopulation.getFirstFront().membersAtThisFront;
                    table.setBestMembersForNonDominatedTable();
                }

            }
            else
            {
                Population.weightedAverage.establishWeightedAverageRelationsForTable(table.tablePopulation,table.mask);
                table.setBestMembersByWeightedAverage();
            }

        }


        //Printer.printTables(this);//todo
        Utils.stop();//todo
    }



    @Override
    public void mainLoop(Problem problem) {
        while(genCounter < Constants.NUMBER_OF_GENERATIONS) {

            //System.out.println("Generation "+genCounter);//todo
            if (genCounter % 50 ==0) TableFunctions.resetContributionAndConvergence(this);

            SelectionTables selectionTables = new SelectionTables();

            ArrayList<Table> parentTables = selectionTables.selectTables(tables,"AEMMT");
            Population parentsPopulation = SelectionRankWeightedAverage.selectParents(parentTables.get(0),parentTables.get(1));
            Population children = problem.crossover.crossoverAndMutation(parentsPopulation);
            recalculateObjectiveFunctions(problem,children);
            super.copyMaskToChildren(parentsPopulation, children);
            this.insertMemberOnTables(children.population.get(0), problem);
            genCounter++;

        }

    }

    @Override
    public void insertMemberOnTables(Member newMember, Problem problem) {
        boolean shouldIncreaseContribution = false;
        ArrayList<Integer> positionsToIncrease = new ArrayList<>();
        int tablePosition = 0;


        for (Table table : tables)
        {

            if (table.isNonDominatedTable)
                shouldIncreaseContribution = insertionForNonDominatedTable(table, newMember.deepCopy(), problem);

            else
                shouldIncreaseContribution = insertionForWeightedAverageTable(table,newMember.deepCopy());


            if (table.mask.equals(newMember.parentTableMask1) || table.mask.equals(newMember.parentTableMask2))
                positionsToIncrease.add(tablePosition);

            tablePosition++;

        }

        if (shouldIncreaseContribution) increaseContribution(positionsToIncrease);
    }

    private boolean insertionForWeightedAverageTable(Table table, Member newMember) {


        Member worstMemberOfTable = table.tablePopulation.population.get(Constants.TABLE_SIZE-1);
        if (worstMemberOfTable.weightedAverage > newMember.weightedAverage){
            table.tablePopulation.population.remove(Constants.TABLE_SIZE-1);
            Member newMemberWithWeightedAverage = WeightedAverage.calculateWeightedAverageForSingleMember(newMember);
            Utils.insertMemberOnCrescentOrderedArrayByWeightedAverage(newMemberWithWeightedAverage,table.tablePopulation.population);

            return true;
        }

        return false;
    }


    private boolean insertionForNonDominatedTable(Table table, Member newMember, Problem problem) {
        boolean shouldIncreaseContribution = false;

        table.tablePopulation.addMember(newMember);
        table.tablePopulation.fastNonDominatedSort();

        if (Problem.instanceOfMemberIsPresent(table.tablePopulation.getFirstFront(),newMember)){
            shouldIncreaseContribution = true;
        }

        Problem.removeSimilar(table.tablePopulation.getFirstFront(),problem);
        table.tablePopulation.population = table.tablePopulation.getFirstFront().membersAtThisFront;


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
        return tables;
    }

    @Override
    int setQtdTables() {

        int nonDominatedTable = 1;
        int qtdTables =0;
        for (int i = 1; i <= Constants.PROBLEM_SIZE ; i++) {
            qtdTables += TableFunctions.fact(Constants.PROBLEM_SIZE) / (fact(i) * fact(Constants.PROBLEM_SIZE - i));
        }

        return  qtdTables + nonDominatedTable;

    }

    @Override
    void updateCurrentMask(int index){
        currentMask = new ArrayList<>();
        for (int i = 0; i < decimalRepresentationOfObjectives.columns; i++) {
            int number = decimalRepresentationOfObjectives.decimalMatrix[index][i];
            if (number != 0)
                currentMask.add(number);
        }
    }

    public void buildTables(Population population ){
        TableFunctions.setQtdMembersOfATable();
        Constants.QTD_TABLES = this.setQtdTables();
        TableFunctions.buildMasks();

        for(int i=0;i<Constants.QTD_TABLES;i++)
        {
            this.updateCurrentMask(i);
            this.addTable(TableFunctions.currentMask);
        }

    }


    private void recalculateObjectiveFunctions(Problem problem, Population children) {
        for (Member m: children.population)
        {
            m.resultOfFunctions = new ArrayList<>();
        }
        problem.evaluateAgainstObjectiveFunctions(children);
    }

}
