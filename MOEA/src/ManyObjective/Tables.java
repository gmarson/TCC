package ManyObjective;
import Constants.*;
import Population.*;
import Problems.*;
import Utilities.*;


import java.util.ArrayList;

/**
 * Created by gabrielm on 07/03/17.
 */
public abstract class Tables {

    public  static  Population parentPopulation;
    public  static  ArrayList<Table> tables = new ArrayList<>();
    private static  Matrix binaryRepresentationOfObjectives;
    public  static  Matrix decimalRepresentationOfObjectives;
    public  static  ArrayList<Integer> currentMask = new ArrayList<>();
   

    public static int setQtdTables(){

        int nonDominatedTable = 1;
        int qtdTables =0;
        for (int i = 1; i <=Constants.PROBLEM_SIZE ; i++) {
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


    public static void fillTablesAEMMT(){
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

    public static void fillTablesAEMMD(){

    }

}
