package PerformanceMetrics;

import Population.*;
import Problems.Problem;

/**
 * Created by gabrielm on 04/05/17.
 */
public class ParetoSubset extends Metrics{

    private double gotRight;
    private double result;
    public ParetoSubset(Problem problem) {
        super(problem);
        gotRight = 0.0;
        result = 0.0;
    }


    @Override
    public void estimateBasedOnMetric(Population population, Population bestPareto) {

        for (Member normalMember: population.population)
        {

            if ( isIn(normalMember,bestPareto))
            {
                this.gotRight++;
            }
        }
        result =  (this.gotRight / (double) bestPareto.population.size()) * 100;
    }


    @Override
    public void messageAfterProcess() {
        System.out.println("(ps) Porcentagem dos elementos da fronteira achada que estão na fronteira de pareto: "+result);
        System.out.println("(ps - qtd) Quantidade dos elementos da fronteira achada que estão na fronteira de pareto: "+gotRight);

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
