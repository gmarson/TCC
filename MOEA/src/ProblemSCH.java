import java.util.ArrayList;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class ProblemSCH implements Problem {

    public ProblemSCH(){}


    @Override
    public ArrayList<Member> generateRandomMembers() {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
            population.add(new Member(Utils.getRandom(Constants.MIN_MEMBER_VALUE, Constants.MAX_MEMBER_VALUE)));
        }        


        population.remove(0);
        population.add(new Member(1));

        return population;
    }

    @Override
    public void evaluateAgainstObjectiveFunctions(Population p) {
        for (Member m: p.population)
        {
            applyFunctions(m);
        }
    }


    public void applyFunctions(Member member)
    {
        int valueOfMember = (int) member.value;
        member.resultOfFunctions.add(firstFunction(valueOfMember));
        member.resultOfFunctions.add(secondFunction(valueOfMember));
    }

    public double firstFunction(int valueOfMember)
    {
        double appliedValue;
        appliedValue =  valueOfMember * valueOfMember;
        return appliedValue;
    }

    public double secondFunction(int valueOfMember)
    {
        double appliedValue;
        valueOfMember = valueOfMember -2;
        appliedValue =  valueOfMember * valueOfMember;
        return appliedValue;
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
    

}
