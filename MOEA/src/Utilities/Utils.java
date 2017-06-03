package Utilities;

import Constants.Constants;
import Population.Member;

import java.util.*;

import static java.lang.Math.*;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public abstract class Utils {

    private static Random random = new Random();

    public static void setSeed(int SEED)
    {
        random = new Random(SEED);
    }

    public static int getRandom(int min, int max)
    {
        max = max -1;
        return random.nextInt((max - min) + 1) + min;
    }

    public static ArrayList<Integer> getRandomBinaryArray(int sizeOfArray)
    {
        ArrayList<Integer> binaryArray = new ArrayList<>();
        for (int i = 0; i < sizeOfArray; i++) {
            binaryArray.add(getRandom(0,2));
        }

        return binaryArray;
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

    public static double[] getWeightVector(int size, double totalSum) {
        Random rand = new Random();
        double randNums[] = new double[size], sum = 0;

        for (int i = 0; i < randNums.length; i++) {
            randNums[i] = rand.nextDouble();
            sum += randNums[i];
        }

        for (int i = 0; i < randNums.length; i++) {
            randNums[i] /= sum * totalSum;
        }


        return randNums;
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

    public static ArrayList<Integer> integerToBinary(int number, int sizeOfBinary){
        ArrayList<Integer> binaryNumber = new ArrayList<>();
        while(true){
            if(number < 2)
            {
                binaryNumber.add(0,number);
                for (int i = binaryNumber.size(); i < sizeOfBinary; i++) {
                    binaryNumber.add(0,0);
                }
                return binaryNumber;
            }
            else {
                binaryNumber.add(0,number % 2);
                number = number / 2;
            }

        }
    }

    private static int correctSignal(ArrayList<Integer> binary, int valueOfMember){
        if(binary.get(0) == 1)
            return valueOfMember * -1;

        return valueOfMember;
    }


    public static double euclidianDistance(Member m1, Member m2)
    {
        double result=0.0, difference;

        for (int i = 0; i <m1.resultOfFunctions.size() ; i++) {
            difference = m1.resultOfFunctions.get(i) - m2.resultOfFunctions.get(i);
            result += pow (difference,2);
        }

        return sqrt(result);

    }

    public static double euclidianDistanceBasedOnDistanceVector(Member parentMember, Member childMember){
        double result = 0.0, difference;
        for (int i = 0; i < parentMember.weightVector.vector.length; i++) {
            difference = parentMember.weightVector.vector[i] - childMember.weightVector.vector[i];
            result += pow(difference,2);
        }
        return sqrt(result);
    }

    public static ArrayList<Double> returnOrderedArray(Matrix distanceMatrix, int indexOfMatrix)
    {
        ArrayList<Double> dataArray = new ArrayList<>();
        double dataToBeInserted;
        int j=0;
        while(j < distanceMatrix.columns)
        {
            if (j != indexOfMatrix)
            {
                dataToBeInserted = distanceMatrix.distance[indexOfMatrix][j];
                insertDataOnDescresentOrderedArray(dataToBeInserted,dataArray);

            }
            
            j++;
        }
        return dataArray;
    }

    public static void insertDataOnDescresentOrderedArray(double dataToBeInserted, ArrayList<Double> dataArray)
    {
        if (dataArray.isEmpty())
            dataArray.add(dataToBeInserted);
        else
        {
            int i=0;
            
            while(dataToBeInserted < dataArray.get(i))
            {
                i++;
                if(i == dataArray.size()) break;
            }
            dataArray.add(i,dataToBeInserted);

        }

    }

    public static void insertDataOnCrescentOrderedArray(double dataToBeInserted, ArrayList<Double> dataArray)
    {
        if (dataArray.isEmpty())
            dataArray.add(dataToBeInserted);
        else
        {
            int i=0;

            while(dataToBeInserted > dataArray.get(i))
            {
                i++;
                if(i == dataArray.size()) break;
            }
            dataArray.add(i,dataToBeInserted);

        }

    }

    public static void insertMemberOnCrescentOrderedArrayByWeightedAverage(Member memberToBeInserted, ArrayList<Member> memberArray){
        if (memberArray.isEmpty())
            memberArray.add(memberToBeInserted);
        else
        {
            int i=0;

            while(memberToBeInserted.weightedAverage > memberArray.get(i).weightedAverage)
            {
                i++;
                if(i == memberArray.size()) break;
            }
            memberArray.add(i,memberToBeInserted.deepCopy());

        }
    }

    public static ArrayList<Member> newArrayWithMember(Member member)
    {
        ArrayList<Member> mostCrowdedMembers = new ArrayList<>();
        mostCrowdedMembers.add(member);

        return mostCrowdedMembers;
    }
}
