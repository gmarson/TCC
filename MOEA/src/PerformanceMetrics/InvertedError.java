package PerformanceMetrics;

import Dominance.Dominance;
import Population.*;
import Problems.Problem;

/**
 * Created by gabrielm on 07/06/17.
 */
public class InvertedError extends Metrics{
    private double result;


    public InvertedError(Problem problem) {
        super(problem);
        result = 0.0;
    }

    @Override
    public void estimateBasedOnMetric(Population population, Population bestParetto) {

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
    public void messageAfterProcess() {
        System.out.println("(ier) Porcentagem dos elementos da fronteira normal que dominam a fronteira de paretto: "+result );
    }
}
