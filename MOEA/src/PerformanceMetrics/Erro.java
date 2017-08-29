package PerformanceMetrics;

import Dominance.Dominance;
import Population.*;
import Problems.*;

/**
 * Created by gabrielm on 09/04/17.
 */
public class Erro extends Metrics{

    private double result;

    public Erro(Problem problem) {
        super(problem);
        result = 0.0;
    }

    @Override
    public void estimateBasedOnMetric(Population population, Population bestParetto){

        //super.problem.evaluateAgainstObjectiveFunctions(tablePopulation);
        //super.problem.evaluateAgainstObjectiveFunctions(bestParetto);

        Dominance d = new Dominance();
        double Ei = 0;
        for (Member memberNormal: population.population) {
            for (Member memberParetto: bestParetto.population){
                if (d.dominates(memberNormal,memberParetto)){
                    Ei++;
                    break;
                }
            }
        }
        result =  (Ei / population.population.size()) * 100;
    }


    @Override
    public void messageAfterProcess(){
        System.out.println("(er) Porcentagem dos elementos da fronteira achada que dominam a fronteira de paretto: "+result );
    }


}
