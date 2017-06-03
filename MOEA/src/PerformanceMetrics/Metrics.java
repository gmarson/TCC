package PerformanceMetrics;

import Population.*;
import Problems.*;

/**
 * Created by gabrielm on 09/04/17.
 */
public abstract class Metrics {


    Problem problem;
    abstract public void estimateBasedOnMetric(Population population, Population bestParetto);
    abstract public void messageAfterProcess();

    public Metrics(Problem problem){
        this.problem = problem;
    }


}
