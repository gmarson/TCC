package ManyObjective.TableFunctions;

import Constants.Constants;
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

    private static int genCounter=1;
    public ArrayList<Table> tables = new ArrayList<>();

    @Override
    public void fillTables() {
        parentPopulation.fastNonDominatedSort();

        for(Table table: tables)
        {
            Population auxPopulation = new Population(parentPopulation);


            auxPopulation.fastNonDominatedSort(table.mask);
            table.setBestMembersByRank(new Population(auxPopulation.getFirstFront()));

        }
    }

    @Override
    public void insertMemberOnTables(Member newMember, Problem problem) {


        Scanner s  = new Scanner(System.in);//todo
        //Printer.printTables(this);//todo

        for (Table table :this.tables)
        {

            table.tablePopulation.addMember(newMember.deepCopy());
            table.tablePopulation.fastNonDominatedSort(table.mask);

            if (Problem.instanceOfMemberIsPresent(table.tablePopulation.getFirstFront(),newMember)){
                table.convergence++;
            }


            Problem.removeSimilar(table.tablePopulation.getFirstFront(),problem);
            table.tablePopulation.population = table.tablePopulation.getFirstFront().membersAtThisFront;


        }

        //Printer.printTables(this);//todo
        //s.nextLine();//todo
    }

    @Override
    public void mainLoop(Problem problem) {
        Scanner s = new Scanner(System.in);//todo
        SelectionTables selectionTables = new SelectionTables();

        while(genCounter < Constants.NUMBER_OF_GENERATIONS) {


            if (genCounter % 50 == 0) TableFunctions.resetContributionAndConvergence(this);

            ArrayList<Table> parentTables = selectionTables.selectTables(tables,"AEMMD");
            Population parentsPopulation = SelectionRank.selectParents(parentTables.get(0),parentTables.get(1));
            Population children = problem.crossover.crossoverAndMutation(parentsPopulation);
            recalculateObjectiveFunctions(problem, children);
            super.copyMaskToChildren(parentsPopulation, children);
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
                //update porque o aemmd nao tem as tabelas de um só objetivo
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
