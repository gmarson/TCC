package PerformanceMetrics;

import Population.*;
import Problems.Problem;
import Utilities.Utils;

import static java.lang.Math.sqrt;

/**
 * Created by gabrielm on 21/05/17.
 */
public class GenerationalDistance extends Metrics{


    public GenerationalDistance(Problem problem) {
        super(problem);
    }

    @Override
    public double estimateBasedOnMetric(Population population, Population bestParetto) {
        Double result = 0.0;
        double currentEuclidianDistance;
        double shortestDistance = Double.MAX_VALUE;

        for(Member normal : population.population)
        {
            for (Member paretto: bestParetto.population)
            {
                currentEuclidianDistance = Utils.euclidianDistance(normal,paretto);
                if (currentEuclidianDistance < shortestDistance) shortestDistance = currentEuclidianDistance;
            }

            result += shortestDistance;
        }
        
        return sqrt(result) / (double) population.population.size();
    }

    @Override
    public void messageBeforeResult() {
        System.out.println("(gd) Somatório das menores distâncias: ");
    }
}
