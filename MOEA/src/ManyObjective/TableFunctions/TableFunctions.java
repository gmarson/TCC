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
    abstract public void mainLoop(Problem problem);
    abstract public void addTable(ArrayList<Integer> mask);
    abstract public ArrayList<Table> getTables();
    abstract int setQtdTables();
    abstract public void buildTables(Population population, TableFunctions tableFunctions);

    static Population parentPopulation;

    private static Matrix binaryRepresentationOfObjectives;
    private static Matrix decimalRepresentationOfObjectives;
    static ArrayList<Integer> currentMask = new ArrayList<>();



    static int fact(int n)
    {
        if(n==0 || n==1)
            return 1;

        return fact(n-1) * n;
    }



    static void setQtdMembersOfATable(){
        Constants.TABLE_SIZE = Constants.POPULATION_SIZE / 10;
    }

    static void buildMasks() {
        binaryRepresentationOfObjectives = new Matrix(Constants.QTD_TABLES, Constants.PROBLEM_SIZE+1,true);
        decimalRepresentationOfObjectives = binaryRepresentationOfObjectives.buildDecimalMatrixGivenBinary();
    }

    static void updateCurrentMask(int index){
        currentMask = new ArrayList<>();
        for (int i = 0; i < decimalRepresentationOfObjectives.columns; i++) {
            int number = decimalRepresentationOfObjectives.decimalMatrix[index][i];
            if (number != 0)
                currentMask.add(number);
        }

    }

    public static void resetContributionAndConvergence(TableFunctions tableFunctions){
        for (Table table: tableFunctions.getTables())
            table.resetContributionAndConvergence();
    }


    public void copyMaskToChildren(Population parentsPopulation, Population children){
        for (int i = 0; i < parentsPopulation.population.size(); i++) {
            children.population.get(i).parentTableMask1 = parentsPopulation.population.get(i).parentTableMask1;
            children.population.get(i).parentTableMask2 = parentsPopulation.population.get(i).parentTableMask2;
        }
    }

    public void reset(){
        parentPopulation = null;
        binaryRepresentationOfObjectives = null;
        decimalRepresentationOfObjectives = null;
        currentMask = new ArrayList<>();

    }

}
