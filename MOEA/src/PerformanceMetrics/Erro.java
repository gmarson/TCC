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

        Dominance d = new Dominance();
        double Ei = 0;
        boolean shouldIncreaseCounter;

        for (Member memberNormal: population.population) {
            shouldIncreaseCounter = true;
            for (Member memberParetto: bestParetto.population){
                if (d.dominates(memberParetto,memberNormal)){
                    shouldIncreaseCounter = false;
                    break;
                }
            }
            if (shouldIncreaseCounter) Ei++;

        }
        result =  (1 - ( (Ei / population.population.size()))) *100;
    }


    @Override
    public void messageAfterProcess(){
        System.out.println("(er) porcentagem dos resultados encontrados que são dominados por alguma solução no Pareto de referência: "+result );
    }


}
