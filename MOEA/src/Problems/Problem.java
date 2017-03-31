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
    public abstract ArrayList<Member> generateRandomMembers(int QtdMembers);

    public abstract void applyFunctions(Member member);
    abstract double firstFunction(Member member);
    abstract double secondFunction(Member member);
    public abstract void printResolutionMessage();

    public static boolean memberIsPresent(Front f, Member member){
        for(Member m: f.membersAtThisFront)
        {
            if (m == member) return  true;
        }
        return false;

    }

    private static ArrayList<Member> removeUsingBinaryValue(Front f){
        ArrayList<Member> members = new ArrayList<Member>();
        boolean shouldAdd = true;
        members.add(f.membersAtThisFront.get(0));
        for (int i = 1; i < f.membersAtThisFront.size(); i++) {

            Member memberToBeInserted= f.membersAtThisFront.get(i);
            shouldAdd = true;

            for (int j = 0; j < members.size(); j++) {
                if (memberToBeInserted.binaryValue.equals(members.get(j).binaryValue)){
                    shouldAdd = false;
                    break;
                }

            }

            if (shouldAdd) members.add(memberToBeInserted);

        }
        return  members;
    }

    private static ArrayList<Member> removeUsingDecimalValue(Front f){
        ArrayList<Member> members = new ArrayList<Member>();
        boolean shouldAdd = true;
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

    public  static void removeSimilar(Front f, Problem problem){
        if(ProblemKnapsack.class.isInstance(problem))

            f.membersAtThisFront = removeUsingBinaryValue(f);
        else
            f.membersAtThisFront = removeUsingDecimalValue(f);


    }

}
