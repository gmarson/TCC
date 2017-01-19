import java.util.ArrayList;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class Member {

    //NSGAII variables
    protected double value;
    protected ArrayList<Double> resultOfFunctions = new ArrayList<>();
    protected ArrayList<Member> solutionsThatThisMemberDominates = new ArrayList<>();
    protected int numberOfSolutionsThatDominatesThisMember =0;
    protected int rank = -1;
    protected double crowdingDistanceValue;

    //SPEA2 variables
    protected double rawFitness = 0;
    protected double density = 0;
    protected double fitness = 0;
    protected double strength = 0;
    protected double distanceFromClosestNeighbor;

    //Both Variables
    protected double key;

    public Member(double value)
    {
        this.value = value;
    }
}
