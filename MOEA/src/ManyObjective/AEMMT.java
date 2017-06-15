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

    public TableAEMMT tableAEMMT = new TableAEMMT();
    Population p = new Population();
    public Front paretto = new Front();

    public void runAlgorithm(Problem problem)
    {

        p.population = problem.generateMembers(Constants.POPULATION_SIZE);


        tableAEMMT.buildTables(p);
        tableAEMMT.fillTables(problem,p);
        tableAEMMT.mainLoop(problem);


        //problem.printResolutionMessage();//todo
        //Printer.printNonDominatedTable(tableAEMMT);//todo
        Printer.printBinaryValuesNonDominatedTable(tableAEMMT);//todo
        saveParetto(problem);

        reset();

    }

    private void reset(){
        tableAEMMT = new TableAEMMT();
        p = new Population();
        tableAEMMT.reset();
    }

    private void saveParetto(Problem problem){
        paretto.membersAtThisFront = tableAEMMT.tables.get(tableAEMMT.tables.size()-1).tablePopulation.population;
        //Problem.removeSimilar(paretto,problem);
    }

}
