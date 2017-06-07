package ManyObjective;

import Fronts.Front;
import ManyObjective.TableFunctions.TableAEMMD;
import Population.Population;
import Problems.Problem;
import Constants.*;
import Utilities.Printer;

import java.util.Scanner;

/**
 * Created by gabrielm on 07/03/17.
 */
public class AEMMD {

    public static TableAEMMD tableAEMMD = new TableAEMMD();
    Population p = new Population();
    public Front paretto = new Front();


    public void runAlgorithm(Problem problem)
    {

        p.population = problem.generateMembers(Constants.POPULATION_SIZE);
        problem.evaluateAgainstObjectiveFunctions(p);


        tableAEMMD.buildTables(p);

        tableAEMMD.fillTables();

        tableAEMMD.mainLoop(problem);


        //Printer.printTables(tableAEMMD);//todo
        Printer.printBinaryValuesNonDominatedTable(tableAEMMD);//todo
        saveParetto(problem);
        reset();

    }

    private void reset(){
        tableAEMMD = new TableAEMMD();
        p = new Population();
        tableAEMMD.reset();

    }

    private void saveParetto(Problem problem){
        paretto.membersAtThisFront.addAll(tableAEMMD.tables.get(tableAEMMD.tables.size()-1).tablePopulation.population);
        //Problem.removeSimilar(paretto,problem);
    }



}
