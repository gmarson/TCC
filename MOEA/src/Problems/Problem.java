package Problems;

import Crossover.Crossover;
import Population.*;

import java.util.ArrayList;

import Fronts.*;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public abstract class  Problem {

    public Crossover crossover;

    public abstract void evaluateAgainstObjectiveFunctions(Population p);
    public abstract void evaluateAgainstMask(Population p, int[] mask);

    public abstract ArrayList<Member> generateMembers(int QtdMembers);

    public abstract void applyFunctions(Member member);
    public abstract void applyFunctionsGivenMask(Member member , int[] mask);

    abstract double firstFunction(Member member);
    abstract double secondFunction(Member member);
    public abstract void printResolutionMessage();

    public static boolean instanceOfMemberIsPresent(Front f, Member member){
        for(Member m: f.membersAtThisFront)
        {
            if (m == member) return  true;
        }
        return false;

    }


    public static boolean valueOfMemberIsPresent(Member member, Population p, Problem problem){
        if(ProblemKnapsack.class.isInstance(problem) || ProblemKnapsackFromFile.class.isInstance(problem))
            return checkUsingBinaryValue( member,  p);
        else
            return checkUsingDecimalValue( member,  p);
    }

    private static boolean checkUsingBinaryValue(Member member, Population p){
        for(Member m: p.population)
        {
            if (isEqual(m,member))
            {
                return  true;
            }
        }
        return false;
    }

    private static boolean checkUsingDecimalValue(Member member, Population p){
        for(Member m: p.population)
        {
            if (m.value == member.value) return  true;
        }
        return false;
    }

    private static ArrayList<Member> removeUsingBinaryValue(Front f){
        ArrayList<Member> members = new ArrayList<>();
        boolean shouldAdd;
        members.add(f.membersAtThisFront.get(0));
        for (int i = 1; i < f.membersAtThisFront.size(); i++) {

            Member memberToBeInserted = f.membersAtThisFront.get(i);
            shouldAdd = true;

            for (int j = 0; j < members.size(); j++) {
                if (isEqual(memberToBeInserted, members.get(j))){
                    shouldAdd = false;
                    break;
                }

            }

            if (shouldAdd) members.add(memberToBeInserted);

        }
        return  members;
    }

    private static boolean isEqual(Member memberToBeInserted, Member member) {
        for (int i = 0; i < memberToBeInserted.binaryValue.size(); i++) {
            if ((int)member.binaryValue.get(i) != (int)memberToBeInserted.binaryValue.get(i))
                return false;
        }
        return true;
    }

    private static ArrayList<Member> removeUsingDecimalValue(Front f){
        ArrayList<Member> members = new ArrayList<>();
        boolean shouldAdd;
        members.add(f.membersAtThisFront.get(0));
        for (int i = 1; i < f.membersAtThisFront.size(); i++) {

            Member memberToBeInserted= f.membersAtThisFront.get(i);
            shouldAdd = true;

            for (int j = 0; j < members.size(); j++) {
                if (memberToBeInserted.value == members.get(j).value){
                    shouldAdd = false;
                    break;
                }

            }

            if (shouldAdd) members.add(memberToBeInserted);

        }
        return  members;
    }

    public static void removeSimilar(Front f, Problem problem){
        if(ProblemKnapsack.class.isInstance(problem) || ProblemKnapsackFromFile.class.isInstance(problem))
            f.membersAtThisFront = removeUsingBinaryValue(f);
        else
            f.membersAtThisFront = removeUsingDecimalValue(f);


    }

    public static void removeSimilar(Population p, Problem problem){
        Front f = new Front();
        f.membersAtThisFront = p.population;
        if(ProblemKnapsack.class.isInstance(problem) || ProblemKnapsackFromFile.class.isInstance(problem))
            p.population = removeUsingBinaryValue(f);
        else
            p.population = removeUsingDecimalValue(f);


    }

}
