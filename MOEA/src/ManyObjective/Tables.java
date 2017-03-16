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
        Constants.QTD_TABLES = setQtdTables();
        parentPopulation = population;
        System.out.println(Constants.QTD_TABLES);//todo
        buildMasks();



        for(int i=0;i<Constants.QTD_TABLES;i++)
        {
            updateCurrentMask(i);
            tables.add(new Table(currentMask));
            System.out.println(tables.get(i).mask);
        }

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


    public static void assignBestMembersToTables(){

    }


}
