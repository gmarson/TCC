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
    private Population p ;
    public Front paretto ;

    public void runAlgorithm(Problem problem)
    {
        paretto = new Front();
        p = new Population();
        tableAEMMT = new TableAEMMT(problem);
        tableAEMMT.buildTables();
        p.population = problem.generateMembers(Parameters.TABLE_SIZE * Parameters.QTD_TABLES);
        tableAEMMT.fillTables(p);
        p = tableAEMMT.mainLoop();

        //Printer.printBinaryMembersWithAppliedFunctions(TableAEMMT.nonDominatedMembers);//todo
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
        paretto.membersAtThisFront = p.population;

    }

}
