package ManyObjective.TableFunctions;

import Constants.Constants;
import ManyObjective.*;
import Population.*;
import Problems.*;
import Utilities.Matrix;

import java.util.ArrayList;

/**
 * Created by gabrielm on 30/03/17.
 */
public abstract class TableFunctions {

    abstract public void fillTables();
    abstract public void insertMemberOnTables(Member newMember, Problem problem);

    static  Population parentPopulation;
    public static ArrayList<Table> tables = new ArrayList<>();
    private static Matrix binaryRepresentationOfObjectives;
    private  static  Matrix decimalRepresentationOfObjectives;
    private static  ArrayList<Integer> currentMask = new ArrayList<>();
    public  static TableAEMMT tableAEMMT = new TableAEMMT();

    private static int setQtdTables(){

        int nonDominatedTable = 1;
        int qtdTables =0;
        for (int i = 1; i <= Constants.PROBLEM_SIZE ; i++) {
            qtdTables += fact(Constants.PROBLEM_SIZE) / (fact(i) * fact(Constants.PROBLEM_SIZE - i));
        }

        return  qtdTables + nonDominatedTable;
    }

    private static int fact(int n)
    {
        if(n==0 || n==1)
            return 1;

        return fact(n-1) * n;
    }

    public static void buildTables(Population population, Problem problem){
        setQtdMembersOfATable();
        Constants.QTD_TABLES = setQtdTables();
        parentPopulation = population;
        buildMasks();

        for(int i=0;i<Constants.QTD_TABLES;i++)
        {
            updateCurrentMask(i);
            tables.add(new Table(currentMask));
        }

    }

    private static void setQtdMembersOfATable(){
        Constants.TABLE_SIZE = Constants.POPULATION_SIZE / 10;
    }

    private static void buildMasks() {
        binaryRepresentationOfObjectives = new Matrix(Constants.QTD_TABLES, Constants.PROBLEM_SIZE+1,true);
        decimalRepresentationOfObjectives = binaryRepresentationOfObjectives.buildDecimalMatrixGivenBinary();

    }


    private static void updateCurrentMask(int index){
        currentMask = new ArrayList<>();
        for (int i = 0; i < decimalRepresentationOfObjectives.columns; i++) {
            int number = decimalRepresentationOfObjectives.decimalMatrix[index][i];
            if (number != 0)
                currentMask.add(number);
        }

    }





    public static void resetContributionAndConvergence(){
        for (Table table: tables)
            table.resetContributionAndConvergence();
    }


    public void copyMaskToChildren(Population parentsPopulation, Population children){
        for (int i = 0; i < parentsPopulation.population.size(); i++) {
            children.population.get(i).parentTableMask1 = parentsPopulation.population.get(i).parentTableMask1;
            children.population.get(i).parentTableMask2 = parentsPopulation.population.get(i).parentTableMask2;
        }
    }

}
