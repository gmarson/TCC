import java.util.ArrayList;

/**
 * Created by gabrielm on 13/02/17.
 */
public class ProblemKnapsack extends Problem{

    @Override
    public void evaluateAgainstObjectiveFunctions(Population p)
    {

    }

    @Override
    public ArrayList<Member> generateRandomMembers()
    {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
            population.add(new Member(Utils.getRandomDouble(Constants.MIN_MEMBER_VALUE, Constants.MAX_MEMBER_VALUE)));
        }

        return population;
    }

    @Override
    public void applyFunctions(Member member)
    {

    }

    @Override
    public double firstFunction(int valueOfMember)
    {
        return 0;
    }

    @Override
    public double secondFunction(int valueOfMember)
    {
        return 0;
    }
}
