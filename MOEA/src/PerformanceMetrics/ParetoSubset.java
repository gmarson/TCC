package PerformanceMetrics;

import Dominance.Dominance;
import Population.*;
import Problems.Problem;
import Utilities.Printer;

/**
 * Created by gabrielm on 04/05/17.
 */
public class ParetoSubset extends Metrics{

    private double gotRight;
    private double result;
    private double bestParetoSize;
    public ParetoSubset(Problem problem) {
        super(problem);
        gotRight = 0.0;
        result = 0.0;
    }

    @Override
    public void estimateBasedOnMetric(Population population, Population bestPareto) {

        Dominance  d = new Dominance();

        for (Member normalMember: population.population)
        {
            for (Member paretoMember: bestPareto.population){
                if ( d.dominates(normalMember,paretoMember))
                {
                    this.gotRight++;
                }
            }

        }
        bestParetoSize = bestPareto.population.size();
        result =  ((double) this.gotRight / (double) bestPareto.population.size()) * 100.00;
    }


    @Override
    public void messageAfterProcess() {
        System.out.println("Quantidade de elementos no Paretto: "+bestParetoSize);
        System.out.println("(ps - qtd) Quantidade dos elementos da fronteira achada que dominam fronteira de pareto: "+gotRight);

    }


}