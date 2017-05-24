package PerformanceMetrics;

import Population.*;
import Problems.Problem;

/**
 * Created by gabrielm on 04/05/17.
 */
public class ParetoSubset extends Metrics{


    public ParetoSubset(Problem problem) {
        super(problem);
    }

    @Override
    public double estimateBasedOnMetric(Population population, Population bestPareto) {

        double gotRight = 0.0;
        for (Member normalMember: population.population)
        {

            if ( isIn(normalMember,bestPareto))
            {
                gotRight++;
            }
        }
        return (gotRight / (double) bestPareto.population.size()) * 100;
    }


    @Override
    public void messageBeforeResult() {
        System.out.println("(ps) Porcentagem dos elementos da fronteira achada que estão na fronteira de pareto");
    }

    private boolean isIn(Member normalMember, Population bestPareto){
        for (Member paretoMember: bestPareto.population)
        {
            if (checkEqual(normalMember, paretoMember)) return true;
        }
        return false;
    }

    private boolean checkEqual(Member normalMember, Member paretoMember) {
        for (int i = 0; i < normalMember.binaryValue.size(); i++) {
            if ((int) normalMember.binaryValue.get(i) != (int) paretoMember.binaryValue.get(i))
            {
                return false;
            }
        }
        return true;
    }


}
