package ManyObjective.TableFunctions;

import Constants.*;
import Dominance.Dominance;
import ManyObjective.*;
import Population.*;
import Problems.*;
import Selections.SelectionRankWeightedAverage;
import Selections.SelectionTables;
import Utilities.*;
import WeightedAverage.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gabrielm on 30/03/17.
 */
public class TableAEMMT extends TableFunctions{

    private static int genCounter=0;
    public static ArrayList<Table> tables = new ArrayList<>();
    public static Population nonDominatedMembers = new Population();
    private static Problem problem;

    public TableAEMMT(Problem problem){
        TableAEMMT.problem = problem;
    }

    @Override
    public void fillTables(Population p){

        for(Table table: tables)
        {
            table.tablePopulation = p.deepCopy();
            problem.evaluateAgainstMask(table.tablePopulation,table.mask);

            if (table.isNonDominatedTable) {
                table.organizeNonDominatedTable(true);
            }
            else
                table.organizeWeightedAverageTable();
        }
    }

    @Override
    public void mainLoop() {
        SelectionTables selectionTables = new SelectionTables();
        while(genCounter < Constants.NUMBER_OF_GENERATIONS) {

            System.out.println("Generation "+genCounter);//todo

            if (genCounter % Constants.RESET_ON_GEN == 0)
                TableFunctions.resetContributionAndConvergence(this);

            ArrayList<Table> parentTables = selectionTables.selectTables(tables,"AEMMT");

            Population parentsPopulation = SelectionRankWeightedAverage.selectParents(parentTables.get(0),parentTables.get(1));

            Population children = problem.crossover.crossoverAndMutation(parentsPopulation);

            super.copyMaskToChildren(parentsPopulation, children);

            this.insertMemberOnTables(children.population.get(0));
            this.insertMemberOnTables(children.population.get(1));

            genCounter++;
        }

        getNonDominatedMembers(problem);

    }

    @Override
    public void insertMemberOnTables(Member newMember) {
        boolean haveToIncreaseContribution = false, shouldIncreaseContribution;
        ArrayList<Integer> positionsToIncrease = new ArrayList<>();
        int tablePosition = 0;
        int pointsToIncrease = 0;

        for (Table table : tables)
        {
            if (table.isNonDominatedTable)
            {
                shouldIncreaseContribution = insertionForNonDominatedTable(table, newMember.deepCopy(), problem);
            }
            else {
                shouldIncreaseContribution = insertionForWeightedAverageTable(table, newMember.deepCopy(), problem);
            }

            if (shouldIncreaseContribution) {
                haveToIncreaseContribution = true;
                pointsToIncrease++;
            }

            if (Arrays.equals(table.mask, newMember.parentTableMask1) || Arrays.equals(table.mask, newMember.parentTableMask2))
                positionsToIncrease.add(tablePosition);

            tablePosition++;

        }
        if (haveToIncreaseContribution) increaseContribution(positionsToIncrease,pointsToIncrease);

    }

    private boolean insertionForWeightedAverageTable(Table table, Member newMember, Problem problem) {

        if(Problem.valueOfMemberIsPresent(newMember,table.tablePopulation,problem)){
            return false;
        }

        Member worstMemberOfTable = table.tablePopulation.population.get(Constants.TABLE_SIZE-1);
        problem.applyFunctionsGivenMask(newMember,table.mask);
        WeightedAverage.calculateWeightedAverage(newMember);

        if (worstMemberOfTable.weightedAverage > newMember.weightedAverage){

            table.tablePopulation.population.remove(Constants.TABLE_SIZE-1);
            Utils.insertMemberOnCrescentOrderedArrayByWeightedAverage(newMember,table.tablePopulation.population);

            return true;
        }

        return false;
    }

    private boolean insertionForNonDominatedTable(Table table, Member newMember, Problem problem) {

        if (Problem.valueOfMemberIsPresent(newMember,table.tablePopulation,problem)
                || table.tablePopulation.population.size() == Constants.TABLE_SIZE){
            return false;
        }


        problem.applyFunctionsGivenMask(newMember,table.mask);
        table.tablePopulation.addMember(newMember);
        table.organizeNonDominatedTable(true);

        return Problem.valueOfMemberIsPresent(newMember, table.tablePopulation, problem);
    }

    private void increaseContribution(ArrayList<Integer> positionsToIncrease, int pointsToIncrease)
    {
        if(positionsToIncrease.size() == 1) positionsToIncrease.add(positionsToIncrease.get(0));

        tables.get(positionsToIncrease.get(0)).contribution+= pointsToIncrease;
        tables.get(positionsToIncrease.get(1)).contribution+= pointsToIncrease;
    }

    @Override
    public void reset(){
        super.reset();
        genCounter = 1;
        tables = new ArrayList<>();
    }

    @Override
    public void addTable(int[] mask){
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
        int sizeOfMask = decimalRepresentationOfObjectives.sizeOfNonZeroElementsInDecimalMatrixRow[index];
        currentMask = new int[sizeOfMask];

        for (int i = 0, j=0; i < decimalRepresentationOfObjectives.columns; i++) {
            int number = decimalRepresentationOfObjectives.decimalMatrix[index][i];

            if (number != 0){
                currentMask[j] = number;
                j++;
            }
        }
    }

    public void buildTables(){
        TableFunctions.setQtdMembersOfATable();
        Constants.QTD_TABLES = this.setQtdTables();
        TableFunctions.buildMasks();

        for(int i=0;i<Constants.QTD_TABLES;i++)
        {
            this.updateCurrentMask(i);
            this.addTable(TableFunctions.currentMask);
        }
    }

    private void getNonDominatedMembers(Problem problem) {

        int[] nonDominatedMask = new int[0];
        for(Table table: tables){
            for (Member member : table.tablePopulation.population){

                if (!Problem.valueOfMemberIsPresent(member,nonDominatedMembers,problem)){
                    problem.applyFunctionsGivenMask(member,nonDominatedMask);
                    nonDominatedMembers.addMember(member);
                }
            }
        }

        nonDominatedMembers.fastNonDominatedSort();
        nonDominatedMembers.population = nonDominatedMembers.getFirstFront().membersAtThisFront;
    }
}
