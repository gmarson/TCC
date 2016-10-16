import java.util.ArrayList;

/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

public abstract class Mutation {

    private static int mutationRate = (int) Population.MUT_RATE;

    public static boolean checkForMutation()
    {
        if(Utils.getRandom(99,0) <= mutationRate)
        {
            return true;
        }
        return false;
    }

    public static void doMutation(Member memberToBeMutated)
    {
        double oldData = memberToBeMutated.getData();
        double addToOldData;
        ProblemSCH sch = ProblemSCH.getInstance();

        double maxInterval = sch.getMaxValue() - oldData;
        if(maxInterval > oldData)
        {
            addToOldData = Utils.getRandomDouble(maxInterval,oldData);
        }
        else
        {
            addToOldData = Utils.getRandomDouble(oldData,maxInterval);
        }

        memberToBeMutated.setData(oldData + addToOldData);
    }

}
