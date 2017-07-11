package ManyObjective;

import Fronts.Front;
import ManyObjective.TableFunctions.TableAEMMD;
import Population.Population;
import Problems.Problem;
import Constants.*;
import Utilities.Printer;
import Utilities.Utils;

import java.util.Scanner;

/**
 * Created by gabrielm on 07/03/17.
 */
public class AEMMD {

    private static TableAEMMD tableAEMMD = new TableAEMMD();
    private Population p = new Population();
    public Front paretto = new Front();


    public void runAlgorithm(Problem problem)
    {
        tableAEMMD.buildTables();

        p.population = problem.generateMembers(Constants.POPULATION_SIZE * Constants.QTD_TABLES);

        tableAEMMD.fillTables(problem,p);

        tableAEMMD.mainLoop(problem);

        //Printer.printTables(tableAEMMD);//todo
        Printer.printBinaryMembersWithAppliedFunctions(TableAEMMD.nonDominatedMembers);//todo
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
        paretto.membersAtThisFront.addAll(TableAEMMD.nonDominatedMembers.population);
        //Problem.removeSimilar(paretto,problem);
    }



}
