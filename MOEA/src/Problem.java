import java.util.ArrayList;


/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public abstract class  Problem {

    Crossover crossover;

    public abstract void evaluateAgainstObjectiveFunctions(Population p);
    public abstract ArrayList<Member> generateRandomMembers();

    public abstract void applyFunctions(Member member);
    public abstract double firstFunction(Member member);
    public abstract double secondFunction(Member member);
    public abstract void printResolutionMessage();

}
