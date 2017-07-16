package ManyObjective.TableFunctions;

import Constants.Constants;
import Dominance.Dominance;
import Fronts.Front;
import ManyObjective.*;
import Population.*;
import Problems.*;
import Selections.SelectionRank;
import Selections.SelectionTables;
import Utilities.Printer;
import Utilities.Utils;
import com.sun.java.browser.plugin2.DOM;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gabrielm on 30/03/17.
 */
public class TableAEMMD extends TableFunctions{

    private static int genCounter=0;
    private static ArrayList<Table> tables = new ArrayList<>();
    public static Population nonDominatedMembers = new Population();
    private static Problem problem;

    public TableAEMMD(Problem problem){
        TableAEMMD.problem = problem;
    }

    @Override
    public void fillTables(Population p) {

        for(Table table: tables)
        {
            //todo tem que otimizar aki
            table.tablePopulation = p.deepCopy();
            problem.evaluateAgainstMask(table.tablePopulation,table.mask);
            table.organizeNonDominatedTable(false);
        }

    }

    @Override
    public void insertMemberOnTables(Member newMember) {

        for (Table table :tables)
        {
            problem.applyFunctionsGivenMask(newMember,table.mask);

            if (!Problem.valueOfMemberIsPresent(newMember,table.tablePopulation,problem))
            {
                //todo tem que otimizar aki
                table.tablePopulation.addMember(newMember.deepCopy());
                table.organizeNonDominatedTable(false);

                if (Problem.valueOfMemberIsPresent(newMember,table.tablePopulation,problem)){
                    table.convergence++;
                }
            }
        }

    }

    @Override
    public void mainLoop() {

        SelectionTables selectionTables = new SelectionTables();

        while(genCounter < Constants.NUMBER_OF_GENERATIONS ) {

            System.out.println("GenCounter: "+genCounter);//todo

            if (genCounter % Constants.RESET_ON_GEN == 0) TableFunctions.resetContributionAndConvergence(this);

            ArrayList<Table> parentTables = selectionTables.selectTables(tables,"AEMMD");
            Population parentsPopulation = SelectionRank.selectParents(parentTables.get(0),parentTables.get(1));
            Population children = problem.crossover.crossoverAndMutation(parentsPopulation);

            this.insertMemberOnTables(children.population.get(0));
            this.insertMemberOnTables(children.population.get(1));
            genCounter++;


        }

        this.getNonDominatedMembers();
    }

    @Override
    public void addTable(int[] mask) {
        tables.add(new Table(mask));
    }

    @Override
    public ArrayList<Table> getTables() {
        return tables;
    }

    @Override
    int setQtdTables() {
        int singleObjectiveTables = Constants.PROBLEM_SIZE;
        int qtdTables =0;
        for (int i = 1; i <= Constants.PROBLEM_SIZE ; i++) {
            qtdTables += TableFunctions.fact(Constants.PROBLEM_SIZE) / (fact(i) * fact(Constants.PROBLEM_SIZE - i));
        }

        return  qtdTables - singleObjectiveTables;
    }

    @Override
    void updateCurrentMask(int index){
        int sizeOfMask = decimalRepresentationOfObjectives.sizeOfNonZeroElementsInDecimalMatrixRow[index];
        currentMask = new int[sizeOfMask];
        for (int i = 0, j=0; i < decimalRepresentationOfObjectives.columns; i++) {
            int number = decimalRepresentationOfObjectives.decimalMatrix[index][i];
            if (number != 0)
            {
                currentMask[j] = number;
                j++;
            }

        }
    }

    @Override
    public void buildTables(){
        TableFunctions.setQtdMembersOfATable();
        Constants.QTD_TABLES = this.setQtdTables();
        TableFunctions.buildMasks(Constants.PROBLEM_SIZE +1);

        this.updateCurrentMask(0);
        int i =0;
        int tableCounter = 0;
        while(tableCounter<Constants.QTD_TABLES )
        {
            this.updateCurrentMask(i);
            while (currentMask.length == 1 || currentMask.length == Constants.PROBLEM_SIZE){
                i++;
                this.updateCurrentMask(i);
            }

            this.addTable(TableFunctions.currentMask);
            tableCounter++;

            i++;
        }
    }

    @Override
    public void reset(){
        super.reset();
        tables = new ArrayList<>();
        genCounter = 0;
    }

    private void getNonDominatedMembers() {

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
