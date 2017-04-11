package PerformanceMetrics;

import Dominance.Dominance;
import Population.*;
import Problems.*;

/**
 * Created by gabrielm on 09/04/17.
 */
public class Erro extends Metrics{

    public Erro(Problem problem) {
        super(problem);
    }

    @Override
    public double estimateBasedOnMetric(Population population, Population bestParetto){

        //super.problem.evaluateAgainstObjectiveFunctions(population);
        //super.problem.evaluateAgainstObjectiveFunctions(bestParetto);

        Dominance d = new Dominance();
        double Ei = 0;
        for (Member memberNormal: population.population) {
            for (Member memberParetto: bestParetto.population){
                if (d.dominates(memberParetto,memberNormal)){
                    Ei++;
                    break;
                }
            }

        }

        return Ei / population.population.size();
    }


    @Override
    public void messageBeforeResult(){
        System.out.println("Taxa de erro é: " );
    }


}
