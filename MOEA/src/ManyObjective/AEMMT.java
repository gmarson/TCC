package ManyObjective;

import Population.Population;
import Problems.Problem;
import Constants.*;
/**
 * Created by gabrielm on 07/03/17.
 */
public class AEMMT {



    public void runAlgorithm(Problem problem)
    {

        Population p = new Population();
        p.population = problem.generateRandomMembers(Constants.POPULATION_SIZE);
        problem.evaluateAgainstObjectiveFunctions(p);
        Tables.buildTables(p,problem);
        Tables.assignBestMembersToTables();

    }

}
