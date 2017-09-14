package ManyObjective.TableFunctions;

import Dominance.Dominance;
import SupportingFiles.Parameters;
import Population.*;
import Problems.*;
import Selections.SelectionRank;
import Selections.SelectionTables;

import java.util.ArrayList;

/**
 * Created by gabrielm on 30/03/17.
 */
public class TableAEMMD extends TableFunctions{

    private static int genCounter = 0;
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
            table.tablePopulation = p.deepCopy();
            problem.evaluateAgainstMask(table.tablePopulation,table.mask);
            table.organizeNonDominatedAEMMDTables();
        }

    }

    @Override
    public void insertMemberOnTables(Member newMember) {

        boolean shouldIncreaseConvergence = false;
        for (Table table :tables)
        {
            if (!Problem.valueOfMemberIsPresent(newMember,table.tablePopulation,problem)){
                problem.applyFunctionsGivenMask(newMember,table.mask);
                shouldIncreaseConvergence = addToNonDominatedPopulation(newMember.deepCopy(),table);
            }

            if (shouldIncreaseConvergence){
                table.convergence++;
            }
        }
    }

    @Override
    public void mainLoop() {

        SelectionTables selectionTables = new SelectionTables();

        while(genCounter < Parameters.NUMBER_OF_GENERATIONS ) {

            System.out.println("GenCounter: "+genCounter);//todo

            if (genCounter % Parameters.RESET_ON_GEN == 0) TableFunctions.resetContributionAndConvergence(this);

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
        int singleObjectiveTables = Parameters.PROBLEM_SIZE;
        int qtdTables =0;
        for (int i = 1; i <= Parameters.PROBLEM_SIZE ; i++) {
            qtdTables += TableFunctions.fact(Parameters.PROBLEM_SIZE) / (fact(i) * fact(Parameters.PROBLEM_SIZE - i));
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
        Parameters.QTD_TABLES = this.setQtdTables();
        TableFunctions.buildMasks(Parameters.PROBLEM_SIZE +1);

        this.updateCurrentMask(0);
        int i =0;
        int tableCounter = 0;
        while(tableCounter< Parameters.QTD_TABLES )
        {
            this.updateCurrentMask(i);
            while (currentMask.length == 1 || currentMask.length == Parameters.PROBLEM_SIZE){
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

        for(Table table: tables){
            for (Member member : table.tablePopulation.population){

                if (!Problem.valueOfMemberIsPresent(member,nonDominatedMembers,problem)){
                    problem.applyFunctions(member);
                    nonDominatedMembers.addMember(member);
                }
            }
        }

        nonDominatedMembers.fastNonDominatedSort();
        nonDominatedMembers.population = nonDominatedMembers.getFirstFront().membersAtThisFront;
    }

    private static boolean addToNonDominatedPopulation(Member member, Table table){
        Dominance dominance = new Dominance();
        ArrayList<Member> toBeRemoved = new ArrayList<>();
        boolean shouldAddNewMember = true;

        for (Member tableMember : table.tablePopulation.population){

            if (dominance.dominates(member,tableMember)){
                toBeRemoved.add(tableMember);
            }

            if (dominance.dominates(tableMember,member)){
                shouldAddNewMember = false;
            }
        }

        table.tablePopulation.population.removeAll(toBeRemoved);
        if (shouldAddNewMember){
            table.tablePopulation.addMember(member);
        }

        return shouldAddNewMember;
    }

}
