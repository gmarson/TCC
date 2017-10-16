package ManyObjective;

import Fronts.Front;
import ManyObjective.TableFunctions.TableAEMMT;
import ManyObjective.TableFunctions.TableAEMMTUNLIMITED;
import Population.Population;
import Problems.Problem;
import SupportingFiles.Parameters;
import SupportingFiles.Printer;

/**
 * Created by gabrielm on 10/14/17.
 * Project : TCC.
 */
public class AEMMTUNLIMITED {

    private TableAEMMTUNLIMITED tableAEMMTUNLIMITED;
    private Population p = new Population();
    public Front pareto = new Front();

    public void runAlgorithm(Problem problem)
    {

        tableAEMMTUNLIMITED = new TableAEMMTUNLIMITED(problem);
        tableAEMMTUNLIMITED.buildTables();
        p.population = problem.generateMembers(Parameters.TABLE_SIZE * Parameters.QTD_TABLES);
        tableAEMMTUNLIMITED.fillTables(p);
        p = tableAEMMTUNLIMITED.mainLoop();

        //Printer.printBinaryMembersWithAppliedFunctions(p);//todo
        //problem.printResolutionMessage();//todo
        //Printer.printNonDominatedTable(tableAEMMT);//todo
        //Printer.printBinaryValuesNonDominatedTable(tableAEMMT);//todo
        //Printer.printTables(tableAEMMT);//todo

        saveParetto(problem);

        reset(problem);

    }

    private void reset(Problem problem){
        tableAEMMTUNLIMITED = new TableAEMMTUNLIMITED(problem);
        p = new Population();
        tableAEMMTUNLIMITED.reset();
    }

    private void saveParetto(Problem problem){
        pareto.membersAtThisFront = p.population;

    }


}
