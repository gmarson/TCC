import java.util.ArrayList;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class ProblemSCH extends Problem {

    protected int MAX_MEMBER_VALUE = 1000, MIN_MEMBER_VALUE = -1000;
    static int MAX_BINARY_LEN = 11;

    public ProblemSCH(){}


    @Override
    public ArrayList<Member> generateRandomMembers() {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < NSGAII.POPULATION_SIZE; i++) {
            population.add(new Member(Utils.getRandom(MIN_MEMBER_VALUE, MAX_MEMBER_VALUE)));
        }


        return population;
    }

    @Override
    public void evaluateAgainstObjectiveFunctions(Population p) {
        for (Member m: p.population)
        {
            applyFunctions(m);
        }
    }

    public void applyFunctions(Member member)
    {
        int valueOfMember = (int) member.value;
        member.resultOfFunctions.add(firstFunction(valueOfMember));
        member.resultOfFunctions.add(secondFunction(valueOfMember));
    }

    public double firstFunction(int valueOfMember)
    {
        double appliedValue;
        appliedValue =  valueOfMember * valueOfMember;
        return appliedValue;
    }

    public double secondFunction(int valueOfMember)
    {
        double appliedValue;
        valueOfMember = valueOfMember -2;
        appliedValue =  valueOfMember * valueOfMember;
        return appliedValue;
    }
}
