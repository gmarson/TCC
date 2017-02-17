import java.util.ArrayList;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class Member {

    //NSGAII variables
    protected ArrayList<Double> resultOfFunctions = new ArrayList<>();
    protected ArrayList<Member> solutionsThatThisMemberDominates = new ArrayList<>();
    protected int numberOfSolutionsThatDominatesThisMember =0;
    protected int rank = -1;
    protected double crowdingDistanceValue;

    //SPEA2 variables
    protected double rawFitness = -1;
    protected double density = -1;
    protected double fitness = -1;
    protected double strength = -1;
    protected double sigma = -1;
    protected ArrayList<Double> distances = new ArrayList<>();

    //Both Variables
    protected double value;
    protected double key;
    protected ArrayList<Integer> binaryValue;

    public Member(ArrayList<Integer> binaryValue) {this.binaryValue = binaryValue;}

    public Member(double value)
    {
        this.value = value;
    }

    public Member deepCopy()
    {
        Member newMember = new Member(this.value);
        newMember.rawFitness = this.rawFitness;
        newMember.density = this.density;
        newMember.fitness = this.fitness;
        newMember.strength = this.strength;
        newMember.rawFitness = this.rawFitness;
        newMember.resultOfFunctions = this.resultOfFunctions;
        newMember.distances = this.distances;

        return newMember;
    }






}
