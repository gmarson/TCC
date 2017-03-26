package Problems;

import Crossover.Crossover;
import Population.*;

import java.util.ArrayList;


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

    public static boolean memberIsPresent(Population p, Member member, Problem problem){
        if(ProblemKnapsack.class.isInstance(problem))
            return checkUsingBinary(p, member, problem);
        else
           return checkUsingDecimal(p, member, problem);

    }

    private static boolean checkUsingBinary(Population p, Member member, Problem problem){
        for (Member m : p.population){
            if (m.binaryValue.equals(member.binaryValue)) return true;
        }
        return  false;
    }

    private static boolean checkUsingDecimal(Population p, Member member, Problem problem){
        for (Member m: p.population){
            if(m.value== member.value) return true;
        }
        return false;
    }

}
