package PerformanceMetrics;

import Population.*;
import Problems.*;

/**
 * Created by gabrielm on 09/04/17.
 */
public abstract class Metrics {


    Problem problem;
    abstract public double estimateBasedOnMetric(Population population, Population bestParetto);
    abstract public void messageBeforeResult();

    public Metrics(Problem problem){
        this.problem = problem;
    }


}
