package ManyObjective.TableFunctions;

import Constants.Constants;
import ManyObjective.*;
import Population.*;
import Problems.*;
import Selections.SelectionRank;
import Selections.SelectionTables;

import java.util.ArrayList;

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
            Population testPopulation = new Population(parentPopulation);

            testPopulation.fastNonDominatedSort(table.mask);
            table.setBestMembersByRank(new Population(testPopulation.fronts.allFronts.get(0)));

        }
    }

    @Override
    public void insertMemberOnTables(Member newMember, Problem problem) {
        problem.applyFunctions(newMember);

        for (Table table :this.tables)
        {

            table.pop.addMember(newMember);
            table.pop.fastNonDominatedSort(table.mask);

            if (Problem.instanceOfMemberIsPresent(table.pop.fronts.allFronts.get(0),newMember)){
                table.convergence++;
            }


            Problem.removeSimilar(table.pop.fronts.allFronts.get(0),problem);
            table.pop.population = table.pop.fronts.allFronts.get(0).membersAtThisFront;


        }
    }

    @Override
    public void mainLoop(Problem problem) {
        while(genCounter < Constants.NUMBER_OF_GENERATIONS) {

            //System.out.println("Generation "+genCounter);
            if (genCounter % 50 ==0) TableFunctions.resetContributionAndConvergence(this);

            SelectionTables selectionTables = new SelectionTables();
            ArrayList<Table> parentTables = selectionTables.selectTables(tables,"AEMMD");
            Population parentsPopulation = SelectionRank.selectParents(parentTables.get(0),parentTables.get(1));
            Population children = problem.crossover.crossoverAndMutation(parentsPopulation);
            super.copyMaskToChildren(parentsPopulation, children);
            this.insertMemberOnTables(children.population.get(0), problem);
            genCounter++;

        }
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
        genCounter = 1;
    }

}
