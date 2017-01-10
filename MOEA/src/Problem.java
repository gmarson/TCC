import java.util.ArrayList;


/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public abstract class Problem {


    protected static int PROBLEM_SIZE = 2;


    public Problem() {}


    public abstract void evaluateAgainstObjectiveFunctions(Population p);
    public abstract ArrayList<Member> generateRandomMembers();



}
