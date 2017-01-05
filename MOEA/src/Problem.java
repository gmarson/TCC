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

    public static void printMembersWithAppliedFunctions(Population p)
    {
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Member "+i+ " = "+m.value);
            System.out.println("F1 = "+m.resultOfFunctions.get(0));
            System.out.println("F2 = "+m.resultOfFunctions.get(1));
            System.out.println("Rank = "+m.rank + "\n");
            i++;
        }
    }

    public static void printMembersWithValue(Population p)
    {
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Member "+i+ " = "+m.value);
            i++;
        }
    }

}
