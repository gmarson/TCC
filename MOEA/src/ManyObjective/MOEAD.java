package ManyObjective;

import Constants.Constants;
import Fronts.Front;
import ManyObjective.MOEADFunctions.*;
import Population.Population;
import Problems.Problem;


/**
 * Created by gabrielm on 01/04/17.
 */
public class MOEAD {

    Population p = new Population();


    public void runAlgorithm(Problem problem)
    {
        p.population = problem.generateMembers(Constants.POPULATION_SIZE);



    }

    private void reset(){


    }

    private void saveParetto(Problem problem){


        //Problem.removeSimilar(paretto,problem);
    }

}
