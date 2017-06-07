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

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gabrielm on 30/03/17.
 */
public class TableAEMMD extends  TableFunctions{

    private static int genCounter=0;
    public ArrayList<Table> tables = new ArrayList<>();

    @Override
    public void fillTables() {
        parentPopulation.fastNonDominatedSort();

        for(Table table: tables)
        {
            parentPopulation.fastNonDominatedSort(table.mask);
            table.tablePopulation.population = parentPopulation.getFirstFront().membersAtThisFront;

            //table.setBestMembersByRank(new Population(parentPopulation.getFirstFront()));


        }
    }

    @Override
    public void insertMemberOnTables(Member newMember, Problem problem) {

        for (Table table :this.tables)
        {

            if (Problem.valueOfMemberIsPresent(newMember,table.tablePopulation,problem))
            {
                table.convergence++;
            }
            else
            {
//                if (table.isNonDominatedTable) {
//                    System.out.println("antes de fastnondominatedsort");//todo
//                    for (Front f : table.tablePopulation.fronts.allFronts) {
//                        f.printFront();
//                    }
//                }

                table.tablePopulation.addMember(newMember.deepCopy());

                table.tablePopulation.fastNonDominatedSort(table.mask);

//                if (table.isNonDominatedTable) {
//                    System.out.println("depois de fastnondominatedsort");//todo
//                    for (Front f : table.tablePopulation.fronts.allFronts) {
//                        f.printFront();
//                    }
//                    s.nextLine();//todo
//                }

                Problem.removeSimilar(table.tablePopulation.getFirstFront(),problem);
                table.tablePopulation.population = table.tablePopulation.getFirstFront().membersAtThisFront;

                if (Problem.valueOfMemberIsPresent(newMember,table.tablePopulation,problem)) table.convergence++;
            }


        }

    }

    @Override
    public void mainLoop(Problem problem) {

        SelectionTables selectionTables = new SelectionTables();

        while(genCounter < Constants.NUMBER_OF_GENERATIONS ) {


            if (genCounter % 50 == 0) TableFunctions.resetContributionAndConvergence(this);

            ArrayList<Table> parentTables = selectionTables.selectTables(tables,"AEMMD");
            Population parentsPopulation = SelectionRank.selectParents(parentTables.get(0),parentTables.get(1));
            Population children = problem.crossover.crossoverAndMutation(parentsPopulation);
            recalculateObjectiveFunctions(problem, children);



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
    public void addTable(ArrayList<Integer> mask) {
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
        currentMask = new ArrayList<>();
        for (int i = 0; i < decimalRepresentationOfObjectives.columns; i++) {
            int number = decimalRepresentationOfObjectives.decimalMatrix[index][i];
            if (number != 0)
                currentMask.add(number);
        }
    }

    @Override
    public void buildTables(Population population){
        TableFunctions.setQtdMembersOfATable();
        Constants.QTD_TABLES = this.setQtdTables();
        parentPopulation = population;
        TableFunctions.buildMasks(Constants.PROBLEM_SIZE +1);

        this.updateCurrentMask(0);
        int i =0;
        int tableCounter = 0;
        while(tableCounter<Constants.QTD_TABLES )
        {
            this.updateCurrentMask(i);
            while (currentMask.size() == 1 || currentMask.size() == Constants.PROBLEM_SIZE){
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

}
