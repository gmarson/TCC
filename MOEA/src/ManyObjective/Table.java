package ManyObjective;

import Constants.*;
import Population.*;
import java.util.ArrayList;

/**
 * Created by gabrielm on 11/03/17.
 */
public class Table {

    public Population pop = new Population();
    public int contribution = 0;
    public ArrayList<Integer> mask;
    public boolean isNonDominatedTable;


    public Table(ArrayList<Integer> mask){
        this.mask = mask;
        this.isNonDominatedTable = mask.size() == 0;
    }



}
