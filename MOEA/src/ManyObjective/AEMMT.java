package ManyObjective;

import Fronts.Front;
import ManyObjective.TableFunctions.TableAEMMT;
import Population.Population;
import Problems.*;
import SupportingFiles.Parameters;
import SupportingFiles.Printer;

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
        p.population = problem.generateMembers(Parameters.TABLE_SIZE * Parameters.QTD_TABLES);
        tableAEMMT.fillTables(p);
        tableAEMMT.mainLoop();

        Printer.printBinaryMembersWithAppliedFunctions(TableAEMMT.nonDominatedMembers);//todo
        //problem.printResolutionMessage();//todo
        //Printer.printNonDominatedTable(tableAEMMT);//todo
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
