import java.util.ArrayList;


/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public interface  Problem {

    void evaluateAgainstObjectiveFunctions(Population p);
    ArrayList<Member> generateRandomMembers();



}
