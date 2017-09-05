package Problems;

import SupportingFiles.Constants;
import Crossover.CrossoverArithmetic;
import Population.*;
import SupportingFiles.Utils;

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
    public ArrayList<Member> generateMembers(int QtdMembers) {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < QtdMembers; i++) {
            population.add(new Member(Utils.getRandomDouble(Constants.MIN_MEMBER_VALUE, Constants.MAX_MEMBER_VALUE)));
        }

        return population;
    }

    @Override
    public void applyFunctions(Member member) {
        member.resultOfFunctions.add(firstFunction(member));
        member.resultOfFunctions.add(secondFunction(member));
    }

    @Override
    public double firstFunction(Member member) {
        int valueOfMember = (int) member.value;
        if(valueOfMember <= 1.0) return - valueOfMember;
        if(valueOfMember <= 3.0) return -2.0 + valueOfMember;
        if(valueOfMember <= 4.0) return 4.0 - valueOfMember;
        return -4.0 + valueOfMember;

    }

    @Override
    public double secondFunction(Member member) {
        int valueOfMember = (int) member.value;
        return Math.pow(valueOfMember - 5.0 , 2);
    }

    @Override
    public void printResolutionMessage() {
        System.out.println("Para o problema F2 os melhores indivíduos são");
    }


    @Override
    public void evaluateAgainstMask(Population p, int[] mask){}

    @Override
    public void applyFunctionsGivenMask(Member member , int[] mask){}
}
