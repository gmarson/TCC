import java.util.ArrayList;

/**
 * Created by gabrielm on 01/02/17.
 */
public class ProblemF2 extends  Problem {

    public ProblemF2()
    {
        crossover = new CrossoverArithmetic();
        Constants.PROBLEM_SIZE = 2;
        Constants.MAX_MEMBER_VALUE = 10;
        Constants.MIN_MEMBER_VALUE = -10;
        Constants.MAX_BINARY_LEN = 11;
    }

    @Override
    public void evaluateAgainstObjectiveFunctions(Population p) {
        for (Member member: p.population)
        {
            applyFunctions(member);
        }
    }

    @Override
    public ArrayList<Member> generateRandomMembers() {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
            population.add(new Member(Utils.getRandomDouble(Constants.MIN_MEMBER_VALUE, Constants.MAX_MEMBER_VALUE)));
        }

        return population;
    }

    @Override
    public void applyFunctions(Member member) {
        int valueOfMember = (int) member.value;
        member.resultOfFunctions.add(firstFunction(valueOfMember));
        member.resultOfFunctions.add(secondFunction(valueOfMember));
    }

    @Override
    public double firstFunction(int valueOfMember) {
        if(valueOfMember <= 1.0) return - valueOfMember;
        if(valueOfMember <= 3.0) return -2.0 + valueOfMember;
        if(valueOfMember <= 4.0) return 4.0 - valueOfMember;
        return -4.0 + valueOfMember;

    }

    @Override
    public double secondFunction(int valueOfMember) {
        return Math.pow(valueOfMember - 5.0 , 2);
    }


}
