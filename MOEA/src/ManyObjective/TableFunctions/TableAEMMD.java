package ManyObjective.TableFunctions;

import ManyObjective.*;
import Population.*;
import Problems.*;

import java.util.ArrayList;

/**
 * Created by gabrielm on 30/03/17.
 */
public class TableAEMMD extends  TableFunctions{


    @Override
    public void fillTables() {
        parentPopulation.fastNonDominatedSort();

        for(Table table: tables)
        {
            Population testPopulation = new Population(parentPopulation);

            testPopulation.fastNonDominatedSort(table.mask);
            table.setBestMembersByRank(new Population(testPopulation.fronts.allFronts.get(0)));

        }
    }

    @Override
    public void insertMemberOnTables(Member newMember, Problem problem) {
        problem.applyFunctions(newMember);

        for (Table table :TableFunctions.tables)
        {

            table.pop.addMember(newMember);
            table.pop.fastNonDominatedSort(table.mask);

            if (Problem.memberIsPresent(table.pop.fronts.allFronts.get(0),newMember)){
                table.convergence++;
            }

            table.pop.population = table.pop.fronts.allFronts.get(0).membersAtThisFront;


        }
    }


}
