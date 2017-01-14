
import java.util.*;

import static java.lang.Math.*;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public abstract class Utils {

    static Random random = new Random();

    public static int getRandom(int min, int max)
    {
        max = max -1;
        return random.nextInt((max - min) + 1) + min;
    }

    public static double getRandomDouble(double min, double max)
    {
        return min + (max - min) * random.nextDouble();
    }

    public static ArrayList<Integer> toBinary(int valueOfMember)
    {
        int negative = 0;
        ArrayList<Integer> binaryNymber = new ArrayList<>();
        if (valueOfMember <0)
        {
         valueOfMember *= -1;
         negative = 1;
        }

        while(valueOfMember >=2)
        {
         binaryNymber.add(0, new Integer(valueOfMember % 2 ) );
         valueOfMember = valueOfMember /2;
        }

        binaryNymber.add(0,valueOfMember);
        binaryNymber.add(0,negative);

        return binaryNymber;
    }

    public static int binaryToInteger(ArrayList<Integer> binary)
    {
        int valueOfMember=0;

        int binarySize = binary.size() -1;
        for (int i = binarySize; i > 0 ; i--) {
            if(binary.get(i) == 1)
                valueOfMember += Math.pow(2,(binarySize) - i);
        }
        valueOfMember = correctSignal(binary,valueOfMember);


        return valueOfMember;
    }

    private static int correctSignal(ArrayList<Integer> binary, int valueOfMember){
        if(binary.get(0) == 1)
            return valueOfMember * -1;

        return valueOfMember;
    }


    public static void insertionSort(ArrayList<Member> members, int idOfFunctionToBeEvaluated)
    {
        for (int i = 0; i < members.size(); i++) {
            Member memberBeingCompared = members.get(i);
            double valueOfMemberBeingCompared = memberBeingCompared.resultOfFunctions.get(idOfFunctionToBeEvaluated);
            for (int j = i -1; j >= 0 && members.get(j).resultOfFunctions.get(idOfFunctionToBeEvaluated) > valueOfMemberBeingCompared ; j--) {
                members.set(j+1, members.get(j));
                members.set(j,memberBeingCompared);
            }
        }
    }

    public static void insertionSortCrowding(ArrayList<Member> members)
    {
        for (int i = 0; i < members.size(); i++) {
            Member memberBeingCompared = members.get(i);
            double valueOfMemberBeingCompared = memberBeingCompared.crowdingDistanceValue;
            for (int j = i -1; j >= 0 && members.get(j).crowdingDistanceValue > valueOfMemberBeingCompared ; j--) {
                members.set(j+1, members.get(j));
                members.set(j,memberBeingCompared);
            }
        }


    }

    public static double euclidianDistance(Member m1, Member m2)
    {
        double x1 = m1.resultOfFunctions.get(0);
        double y1 = m1.resultOfFunctions.get(1);
        double x2 = m2.resultOfFunctions.get(0);
        double y2 = m2.resultOfFunctions.get(1);

        return sqrt( pow( ( x1- x2) , 2 ) + pow( ( y1 - y2) , 2) );
    }

}
