import java.util.ArrayList;
import java.util.Random;

/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

public abstract class Population {
    public static int POP_SIZE =5;
    public static int NUM_GER=50;
    public static double CROSS_RATE=20;
    public static double MUT_RATE=1;


    private static ArrayList<Member> population = new ArrayList<Member>();

    private Population()
    {
        this.CROSS_RATE = this.CROSS_RATE/100.0;
        this.MUT_RATE = this.MUT_RATE/100.0;
    }

    public static ArrayList<Member> getInstance(){
        return population;
    }

    public static void generatePopulation()
    {
        Function f = ProblemSCH.getInstance();
        if(f == null)
        {
            System.out.println("Null Function");
            return;
        }

        Member m;
        Random r = new Random();

        double maxValue = f.getMaxValue();
        double minValue = f.getMinValue();

        for(int i=0 ; i<POP_SIZE; i++)
        {
            population.add(new Member(minValue + (maxValue - minValue) * r.nextDouble()));
        }
    }

    public static void printPopulation()
    {
        System.out.println("Population");
        for(int i = 0; i<population.size(); i++)
        {
            System.out.println("I = "+i+" DATA = "+ population.get(i).getData());
        }
    }

    public ArrayList<Member> getPopulation() {
        return population;
    }

    public static void replaceElement(ArrayList<Object> appliedFunctions, int indexOf)
    {
        population.get(indexOf).setResultOfFunctions(appliedFunctions);
    }

    public static void printPopulationDetailed()
    {
        for(int i=0; i<population.size(); i++)
        {
            System.out.println("I"+i);
            population.get(i).printMember();
        }
    }
}
