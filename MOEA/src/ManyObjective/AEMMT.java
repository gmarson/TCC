package ManyObjective;

import Fronts.Front;
import ManyObjective.TableFunctions.TableAEMMT;
import Population.Population;
import Problems.*;
import Constants.*;
import Utilities.Printer;

/**
 * Created by gabrielm on 07/03/17.
 */
public class AEMMT {

    private TableAEMMT tableAEMMT;
    private Population p = new Population();
    public Front paretto = new Front();

    public void runAlgorithm(Problem problem)
    {

        tableAEMMT = new TableAEMMT(problem);
        tableAEMMT.buildTables();
        p.population = problem.generateMembers(Constants.POPULATION_SIZE * Constants.QTD_TABLES);
        tableAEMMT.fillTables(p);
        tableAEMMT.mainLoop();

        Printer.printBinaryMembersWithAppliedFunctions(TableAEMMT.nonDominatedMembers);
        //problem.printResolutionMessage();//todo
        Printer.printNonDominatedTable(tableAEMMT);//todo
        //Printer.printBinaryValuesNonDominatedTable(tableAEMMT);//todo
        //Printer.printTables(tableAEMMT);//todo

        saveParetto(problem);

        reset(problem);

    }

    private void reset(Problem problem){
        tableAEMMT = new TableAEMMT(problem);
        p = new Population();
        tableAEMMT.reset();
    }

    private void saveParetto(Problem problem){
        paretto.membersAtThisFront = TableAEMMT.nonDominatedMembers.population;

    }

}
