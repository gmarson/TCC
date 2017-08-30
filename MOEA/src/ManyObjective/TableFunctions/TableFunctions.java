package ManyObjective.TableFunctions;

import Constants.Constants;
import ManyObjective.*;
import Population.*;
import SupportingFiles.Matrix;

import java.util.ArrayList;

/**
 * Created by gabrielm on 30/03/17.
 */
public abstract class TableFunctions {

    abstract public void fillTables(Population p);
    abstract public void insertMemberOnTables(Member newMember);
    abstract public void mainLoop();
    abstract public void addTable(int[] mask);
    abstract public ArrayList<Table> getTables();
    abstract int setQtdTables();
    abstract void updateCurrentMask(int index);
    abstract public void buildTables();

    private static Matrix binaryRepresentationOfObjectives;
    static Matrix decimalRepresentationOfObjectives;
    static int[] currentMask = new int[0];

    static int fact(int n)
    {
        if(n==0 || n==1)
            return 1;

        return fact(n-1) * n;
    }

    static void setQtdMembersOfATable(){
        Constants.TABLE_SIZE = Constants.POPULATION_SIZE;
    }

    static void buildMasks() {
        binaryRepresentationOfObjectives = new Matrix(Constants.QTD_TABLES, Constants.PROBLEM_SIZE+1,true);
        decimalRepresentationOfObjectives = binaryRepresentationOfObjectives.buildDecimalMatrixGivenBinary();
        decimalRepresentationOfObjectives.sizeOfNonZeroElementsInDecimalMatrixRow = binaryRepresentationOfObjectives.sizeOfNonZeroElementsInDecimalMatrixRow;
    }

    static void buildMasks(int moreColumns){
        binaryRepresentationOfObjectives = new Matrix(Constants.QTD_TABLES + moreColumns, Constants.PROBLEM_SIZE+1,true);
        decimalRepresentationOfObjectives = binaryRepresentationOfObjectives.buildDecimalMatrixGivenBinary();
        decimalRepresentationOfObjectives.sizeOfNonZeroElementsInDecimalMatrixRow = binaryRepresentationOfObjectives.sizeOfNonZeroElementsInDecimalMatrixRow;
    }

    static void resetContributionAndConvergence(TableFunctions tableFunctions){
        for (Table table: tableFunctions.getTables())
            table.resetContributionAndConvergence();
    }

    void copyMaskToChildren(Population parentsPopulation, Population children){
        for (int i = 0; i < parentsPopulation.population.size(); i++) {
            children.population.get(i).parentTableMask1 = parentsPopulation.population.get(i).parentTableMask1;
            children.population.get(i).parentTableMask2 = parentsPopulation.population.get(i).parentTableMask2;
        }
    }

    public void reset(){
        binaryRepresentationOfObjectives = null;
        decimalRepresentationOfObjectives = null;
        currentMask = new int[0];
    }

}
