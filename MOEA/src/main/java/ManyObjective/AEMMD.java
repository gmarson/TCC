package ManyObjective;

import Fronts.Front;
import ManyObjective.TableFunctions.TableAEMMD;
import ManyObjective.TableFunctions.TableAEMMT;
import ManyObjective.TableFunctions.TableAEMMTUNLIMITED;
import Population.Population;
import Problems.Problem;
import SupportingFiles.Parameters;
import SupportingFiles.Printer;

/**
 * Created by gabrielm on 07/03/17.
 */
public class AEMMD {

    private static TableAEMMD tableAEMMD;
    private Population p ;
    public Front paretto ;


    public void runAlgorithm(Problem problem)
    {
        p = new Population();
        paretto = new Front();
        tableAEMMD = new TableAEMMD(problem);

        tableAEMMD.buildTables();

        p.population = problem.generateMembers(Parameters.TABLE_SIZE * Parameters.QTD_TABLES);

        tableAEMMD.fillTables(p);

        p = tableAEMMD.mainLoop();

        //Printer.printTables(tableAEMMD);//todo
        //Printer.printBinaryMembersWithAppliedFunctions(TableAEMMD.nonDominatedMembers);//todo
        //Printer.printBinaryMembersWithAppliedFunctions(TableAEMMD.nonDominatedMembers);

        saveParetto(problem);
        reset(problem);
    }

    private void reset(Problem problem){
        tableAEMMD = new TableAEMMD(problem);
        p = new Population();
        tableAEMMD.reset();

    }

    private void saveParetto(Problem problem){
        paretto.membersAtThisFront = p.population;
        //Problem.removeSimilar(pareto,problem);
    }



}
