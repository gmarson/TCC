package ManyObjective;
import Constants.*;
import Population.*;
import Problems.*;
import Utilities.*;


import javax.swing.text.Utilities;
import java.util.ArrayList;

/**
 * Created by gabrielm on 07/03/17.
 */
public abstract class Tables {

    public static Population parentPopulation;
    public static ArrayList<Table> tables = new ArrayList<>();
    public static Matrix maskHandler;
    public static ArrayList<Integer> currentMask = new ArrayList<>();

    public static int setQtdTables(int numberOfRemainingObjectives, int numberOfTables){
        if(numberOfRemainingObjectives == 2 && Constants.PROBLEM_SIZE == 2) return 4;
        else if (numberOfRemainingObjectives == 2 && Constants.PROBLEM_SIZE > 2) return 0;
        else return setQtdTables(numberOfRemainingObjectives-1,numberOfTables) + numberOfTables*2;

    }

    public static void buildTables(Population population){
        Constants.QTD_TABLES = setQtdTables(Constants.PROBLEM_SIZE,4);
        parentPopulation = population;
        buildMask();




        for(int i=0;i<Constants.QTD_TABLES;i++)
        {
            updateCurrentMask(i);
            tables.add(new Table(currentMask));
        }


    }

    private static void buildMask() {
        maskHandler = new Matrix(Constants.QTD_TABLES, Constants.PROBLEM_SIZE+1,true);

    }


    private static void updateCurrentMask(int index){
        currentMask = new ArrayList<>();
        for (int i = 0; i < maskHandler.columns; i++) {
            currentMask.add(maskHandler.maskHandler[index][i]);
        }

    }


}
