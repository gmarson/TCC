package PerformanceMetrics;

import Dominance.Dominance;
import Population.*;
import Problems.Problem;

/**
 * Created by gabrielm on 04/05/17.
 */
public class ParetoSubset extends Metrics{

    public double gotRight;
    public double result;
    public double bestParetoSize;
    public ParetoSubset(Problem problem) {
        super(problem);
        gotRight = 0.0;
        result = 0.0;
    }

    @Override
    public void estimateBasedOnMetric(Population population, Population bestPareto) {

        Dominance  d = new Dominance();
        boolean shouldIncreaseContribution;

        for (Member normalMember: population.population)
        {
            shouldIncreaseContribution = true;
            for (Member paretoMember: bestPareto.population){

                if ( d.dominates(paretoMember,normalMember))
                {
                    shouldIncreaseContribution = false;
                }

            }
            if (shouldIncreaseContribution) this.gotRight++;

        }
        bestParetoSize = bestPareto.population.size();
        result =  this.gotRight;
    }


    @Override
    public void messageAfterProcess() {

        System.out.println("Quantidade de elementos no Paretto: "+bestParetoSize);
        System.out.println("(ps) Quantidade dos resultados encontrados que são dominados por alguma solução no Pareto de referência: "+gotRight);
    }


}