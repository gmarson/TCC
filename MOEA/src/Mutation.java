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
        String oldString,newString="";
        char c;
        char charArray[];
        for(int i =0 ; i<memberToBeMutated.getBinaryData().length();i++)
        {
            if(Utils.getRandom(99,0) == 1)
            {
                oldString = memberToBeMutated.getBinaryData();
                c = oldString.charAt(i);
                charArray = oldString.toCharArray();
                if(c == '0')
                {
                    charArray[i] = '1';
                }
                else
                {
                    charArray[i] = '0';
                }
                newString = new String(charArray);
                memberToBeMutated.setBinaryData(newString);
            }
        }
    }
}
