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
        Neighboring.createWeightVectorForPopulation(p);
        Solution.setSolutionForPopulation(p);
        Neighboring.setNeighboursOfAllMembers(p);




    }

    private void reset(){


    }

    private void saveParetto(Problem problem){


        //Problem.removeSimilar(paretto,problem);
    }


    /*
    * Ressalvas:
    * o calculo de vizinhanca é feito somente calculando-se o vetor de pesos
    * */

}
