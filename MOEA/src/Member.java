import java.util.ArrayList;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class Member {

    protected double value;
    protected ArrayList<Double> resultOfFunctions = new ArrayList<>();
    protected ArrayList<Member> solutionsThatThisMemberDominates = new ArrayList<>();
    protected int numberOfSolutionsThatDominatesThisMember =0;
    protected int rank = -1;
    protected double crowdingDistanceValue;

    public Member(double value)
    {
        this.value = value;
    }
}
