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

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gabrielm on 30/03/17.
 */
public class TableAEMMD extends  TableFunctions{

    private static int genCounter=0;
    public static ArrayList<Table> tables = new ArrayList<>();

    @Override
    public void fillTables(Problem problem,Population p) {

        for(Table table: tables)
        {
            table.tablePopulation = p.deepCopy();

            problem.evaluateAgainstMask(table.tablePopulation,table.mask);

            table.tablePopulation.fastNonDominatedSort();
            table.tablePopulation.population = table.tablePopulation.getFirstFront().membersAtThisFront;

        }

    }

    @Override
    public void insertMemberOnTables(Member newMember, Problem problem) {

        for (Table table :tables)
        {
            newMember.resultOfFunctions = new ArrayList<>();
            problem.applyFunctionsGivenMask(newMember,table.mask);

            if (Problem.valueOfMemberIsPresent(newMember,table.tablePopulation,problem))
            {
                table.convergence++;
            }
            else
            {

                table.tablePopulation.addMember(newMember.deepCopy());

                table.tablePopulation.fastNonDominatedSort();

                Problem.removeSimilar(table.tablePopulation.getFirstFront(),problem);
                table.tablePopulation.population = table.tablePopulation.getFirstFront().membersAtThisFront;

            }

        }

    }

    @Override
    public void mainLoop(Problem problem) {

        SelectionTables selectionTables = new SelectionTables();

        while(genCounter < Constants.NUMBER_OF_GENERATIONS ) {

            //System.out.println("GenCounter: "+genCounter);//todo

            if (genCounter % 50 == 0) TableFunctions.resetContributionAndConvergence(this);

            ArrayList<Table> parentTables = selectionTables.selectTables(tables,"AEMMD");
            Population parentsPopulation = SelectionRank.selectParents(parentTables.get(0),parentTables.get(1));
            Population children = problem.crossover.crossoverAndMutation(parentsPopulation);

            this.insertMemberOnTables(children.population.get(0), problem);
            genCounter++;


        }
    }

    private void recalculateObjectiveFunctions(Problem problem, Population children) {
        for (Member m: children.population)
        {
            m.resultOfFunctions = new ArrayList<>();
        }
        problem.evaluateAgainstObjectiveFunctions(children);
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
    public void buildTables(Population population){
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

    public void testDomination(){
        int i=0;
        int shoudBeWoried = 0;
        Dominance d = new Dominance();
        for (Table table : tables){
            if (tables.get(i).isNonDominatedTable)
            {
                Table ndt = tables.get(i);
                for (int j = 0; j < tables.size(); j++) {

                    if (!tables.get(j).isNonDominatedTable){

                        for (Member m : tables.get(j).tablePopulation.population){

                            for (Member ndtm : ndt.tablePopulation.population)
                            {
                                if (d.dominates(m,ndtm)){
                                    shoudBeWoried++;
                                }
                                else {
                                    break;
                                }
                            }
                            if (shoudBeWoried == ndt.tablePopulation.population.size())
                            {
                                System.out.println("tem algo de errado!");
                            }

                            shoudBeWoried = 0;
                        }
                    }
                }
            }
            i++;
        }
    }


}
