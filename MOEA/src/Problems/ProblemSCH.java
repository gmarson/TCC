package Problems;

import Constants.Constants;
import Crossover.CrossoverBinary;
import Population.*;
import Utilities.Utils;

import java.util.ArrayList;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class ProblemSCH extends Problem {

    public ProblemSCH()
    {
        crossover = new CrossoverBinary();
        Constants.PROBLEM_SIZE = 2;
        Constants.MAX_MEMBER_VALUE = 1000;
        Constants.MIN_MEMBER_VALUE = -1000;
        Constants.MAX_BINARY_LEN = 11;
    }

    @Override
    public void evaluateAgainstObjectiveFunctions(Population p) {
        for (Member m: p.population)
        {
            applyFunctions(m);
        }
    }

    @Override
    public ArrayList<Member> generateMembers(int QtdMembers) {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < QtdMembers; i++) {
            population.add(new Member(Utils.getRandom(Constants.MIN_MEMBER_VALUE, Constants.MAX_MEMBER_VALUE)));
        }        

        return population;
    }


    @Override
    public void applyFunctions(Member member)
    {
        member.resultOfFunctions.add(firstFunction(member));
        member.resultOfFunctions.add(secondFunction(member));
    }

    @Override
    public double firstFunction(Member member)
    {
        return  member.value * member.value;
    }

    @Override
    public double secondFunction(Member member)
    {
        double valueOfMember = member.value -2;
        return  valueOfMember * valueOfMember;
    }

    @Override
    public void printResolutionMessage() {
        System.out.println("Para o problema SCH os melhores indivíduos são");
    }

    public void checkBestAnswerAppearances(Population p)
    {
        boolean one=false, two=false, zero =false;

        for(Member m : p.population)
        {
            if(m.value == 0) zero =true;
            if(m.value == 1) one =true;
            if(m.value == 2) two=true;
        }

        if (zero) System.out.println("Apareceu o 0");
        if (one)  System.out.println("Apareceu o 1");
        if (two)  System.out.println("Apareceu o 2");

    }


    @Override
    public void evaluateAgainstMask(Population p, int[] mask){}

    @Override
    public void applyFunctionsGivenMask(Member member , int[] mask){}

}
